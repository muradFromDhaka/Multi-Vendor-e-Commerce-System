package com.abc.multiVendorEProject.service.Customer;

import com.abc.multiVendorEProject.DTOs.projectDtos.CartDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto.OrderRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto.OrderResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.ShippingAddressRequestDto;
import com.abc.multiVendorEProject.Util.NotFoundException;
import com.abc.multiVendorEProject.entity.*;
import com.abc.multiVendorEProject.entity.Variant.ProductVariant;
import com.abc.multiVendorEProject.enums.OrderStatus;
import com.abc.multiVendorEProject.enums.PaymentMethod;
import com.abc.multiVendorEProject.enums.PaymentStatus;
import com.abc.multiVendorEProject.enums.VendorOrderStatus;
import com.abc.multiVendorEProject.mapper.OrderMapper;
import com.abc.multiVendorEProject.mapper.ShippingAddressMapper;
import com.abc.multiVendorEProject.repository.*;
import com.abc.multiVendorEProject.repository.VariantRepository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CheckoutService {

    private final CartService cartService;
    private final UserRepository userRepository;
    private final VendorOrderRepository vendorOrderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ShippingAddressRepository shippingAddressRepository;
    private final OrderRepository orderRepository;

    private Cart getCurrentCart() {
        return cartService.getCartEntity();
    }

    private void validateCart(Cart cart) {

        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Your cart is empty");
        }

    }

    @Transactional
    private User getCurrentUser() {

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (auth == null || !auth.isAuthenticated()
                || auth instanceof AnonymousAuthenticationToken) {
            throw new NotFoundException.UnauthorizedException("User not logged in");
        }

        String username = auth.getName();

        return userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private String generateOrderNumber() {

        String orderNumber;

        do {
            orderNumber = "ORD-" + UUID.randomUUID();
        } while (orderRepository.existsByOrderNumber(orderNumber));

        return orderNumber;
    }

    private ShippingAddress buildShippingAddress(
            ShippingAddressRequestDto dto,
            User user) {

        ShippingAddress address = ShippingAddressMapper.toEntity(dto);

        address.setUser(user);

        return address;
    }


    @Transactional
    private ShippingAddress saveShippingAddress(
            OrderRequestDto request,
            User user) {

        if (request.getShippingAddressId() != null) {

             return shippingAddressRepository
                    .findByIdAndUser_UserName(
                            request.getShippingAddressId(),
                            user.getUserName()
                    )
                    .orElseThrow(() ->
                            new RuntimeException("Shipping address not found"));
        }

        ShippingAddress address =
                buildShippingAddress(request.getShippingAddress(), user);

        return shippingAddressRepository.save(address);
    }


    @Transactional
    private void reduceStock(
            List<OrderItem> orderItems) {

        orderItems.forEach(item -> {
            ProductVariant variant = item.getVariant();
            variant.setStock(
                    variant.getStock() - item.getQuantity());
        });
        productVariantRepository.saveAll(
                orderItems.stream()
                        .map(OrderItem::getVariant)
                        .toList()
        );
    }


    @Transactional
    private Order createOrder(
            User user,
            ShippingAddress shippingAddress,
            PaymentMethod paymentMethod,
            CartDto cartSummary) {

        Order order = new Order();

        order.setUser(user);
        order.setOrderNumber(generateOrderNumber());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setShippingAddress(shippingAddress);

        order.setSubtotal(cartSummary.getSubtotal());
        order.setShippingFee(cartSummary.getShippingFee());
        order.setDiscount(cartSummary.getDiscount());
        order.setTotalPrice(cartSummary.getTotalAmount());

        Payment payment = new Payment();
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setAmount(order.getTotalPrice());

        payment.setOrder(order);
        order.setPayment(payment);

        return order;
    }

    @Transactional
    private List<VendorOrder> createVendorOrders(
            Order order,
            List<OrderItem> orderItems) {

        Map<Vendor, List<OrderItem>> grouped =
                orderItems.stream()
                        .collect(Collectors.groupingBy(OrderItem::getVendor));

        BigDecimal distributedShipping = BigDecimal.ZERO;

        int currentVendor = 0;
        int totalVendors = grouped.size();

        List<VendorOrder> vendorOrders = new ArrayList<>();

        for (Map.Entry<Vendor, List<OrderItem>> entry : grouped.entrySet()) {

            Vendor vendor = entry.getKey();

            List<OrderItem> items = entry.getValue();

            VendorOrder vendorOrder = new VendorOrder();

            vendorOrder.setOrder(order);

            vendorOrder.setVendor(vendor);

            vendorOrder.setVendorOrderStatus(VendorOrderStatus.PENDING);

            BigDecimal subtotal = items.stream()
                            .map(OrderItem::getTotalPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

            vendorOrder.setSubtotal(subtotal);

            BigDecimal shippingFee = BigDecimal.ZERO;

            if (order.getShippingFee().compareTo(BigDecimal.ZERO) > 0
                    && order.getSubtotal().compareTo(BigDecimal.ZERO) > 0) {

                if (currentVendor == totalVendors - 1) {

                    // Last vendor gets the remaining shipping
                    shippingFee = order.getShippingFee()
                            .subtract(distributedShipping);

                } else {

                    shippingFee = subtotal
                            .multiply(order.getShippingFee())
                            .divide(
                                    order.getSubtotal(),
                                    2,
                                    java.math.RoundingMode.HALF_UP
                            );

                    distributedShipping =
                            distributedShipping.add(shippingFee);
                }
            }

            vendorOrder.setShippingFee(shippingFee);

            int totalQuantity = items.stream()
                    .mapToInt(OrderItem::getQuantity)
                    .sum();

            BigDecimal discount = BigDecimal.ZERO;

            if (totalQuantity >= 3) {
                discount = subtotal.multiply(PricingConstants.VENDOR_DISCOUNT_RATE);
            }

            vendorOrder.setDiscount(discount);

//            vendorOrder.setTotalPrice(subtotal);

            // Final Total
            vendorOrder.setTotalPrice(
                    subtotal
                            .subtract(discount)
                            .add(vendorOrder.getShippingFee())
            );

            vendorOrder.setVendorOrderNumber(
                    "VORD-"
                            + UUID.randomUUID()
                            + "-"
                            + vendor.getId()
            );

            vendorOrders.add(vendorOrder);

            currentVendor++;
        }

        return vendorOrderRepository.saveAll(vendorOrders);
    }



    @Transactional
    private List<OrderItem> createOrderItems(Order order, Cart cart) {

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {

            OrderItem item = new OrderItem();

            item.setOrder(order);

            ProductVariant variant = productVariantRepository.findById(
                    cartItem.getProductVariant().getId()
            ).orElseThrow(() ->
                    new RuntimeException("Product variant not found"));

            // Quantity validation
            if (cartItem.getQuantity() <= 0) {
                throw new RuntimeException("Invalid quantity");
            }

            // Stock validation
            if (variant.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException(
                        "Insufficient stock for SKU: " + variant.getSku()
                );
            }

            item.setVariant(variant);

            item.setVendor(variant.getProduct().getVendor());

            item.setQuantity(cartItem.getQuantity());


            item.setUnitPrice(cartItem.getUnitPrice());

            cartItem.calculateTotalPrice();

            item.setTotalPrice(cartItem.getTotalPrice());

            orderItems.add(item);
        }

        return orderItemRepository.saveAll(orderItems);
    }

    private void assignVendorOrders(
            List<OrderItem> orderItems,
            List<VendorOrder> vendorOrders) {

        Map<Long, VendorOrder> vendorMap =
                vendorOrders.stream()
                        .collect(Collectors.toMap(
                                vo -> vo.getVendor().getId(),
                                vo -> vo
                        ));

        for (OrderItem item : orderItems) {

            VendorOrder vendorOrder =
                    vendorMap.get(item.getVendor().getId());

            item.setVendorOrder(vendorOrder);
        }

        orderItemRepository.saveAll(orderItems);
    }



//    =======================Original Method=================================

    @Transactional
    public OrderResponseDto placeOrder(OrderRequestDto request) {

        if (request.getShippingAddressId() == null
                && request.getShippingAddress() == null) {
            throw new RuntimeException("Shipping address is required");
        }

        if (request.getPaymentMethod() == null) {
            throw new RuntimeException("Payment method is required");
        }
        
        User user = getCurrentUser();

        Cart cart = getCurrentCart();

        validateCart(cart);

        CartDto cartSummary = cartService.getCart();

        ShippingAddress shippingAddress =
                saveShippingAddress(request, user);

        Order order = createOrder(
                user,
                shippingAddress,
                request.getPaymentMethod(),
                cartSummary
        );

        order = orderRepository.save(order);

        List<OrderItem> orderItems = createOrderItems(order, cart);

        List<VendorOrder> vendorOrders = createVendorOrders(order, orderItems);

        assignVendorOrders(orderItems, vendorOrders);

        try {
            reduceStock(orderItems);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new RuntimeException(
                    "Some products were updated by another customer. Please review your cart."
            );
        }
        cartService.clearCart();

        return OrderMapper.toResponseDto(order);

    }


    public CartDto getCheckoutSummary() {
        return cartService.getCart();
    }

}
