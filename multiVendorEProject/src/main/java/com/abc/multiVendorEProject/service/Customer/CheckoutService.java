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
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
            orderNumber = "ORD-" + System.currentTimeMillis();
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

    private BigDecimal calculateSubtotal(
            List<OrderItem> orderItems) {

        return orderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalPrice(
            BigDecimal subtotal,
            BigDecimal shippingFee,
            BigDecimal discount) {

        return subtotal
                .add(shippingFee)
                .subtract(discount);
    }


    private Order createOrder(
            User user,
            ShippingAddress shippingAddress,
            PaymentMethod paymentMethod) {

        Order order = new Order();

        order.setUser(user);
        order.setOrderNumber(generateOrderNumber());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setShippingAddress(shippingAddress);

        order.setShippingFee(BigDecimal.ZERO);
        order.setDiscount(BigDecimal.ZERO);
        order.setSubtotal(BigDecimal.ZERO);
        order.setTotalPrice(BigDecimal.ZERO);

        Payment payment = new Payment();
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setAmount(order.getTotalPrice());

        payment.setOrder(order);
        order.setPayment(payment);

        return order;
    }

    private List<VendorOrder> createVendorOrders(
            Order order,
            List<OrderItem> orderItems) {

        Map<Vendor, List<OrderItem>> grouped =
                orderItems.stream()
                        .collect(Collectors.groupingBy(OrderItem::getVendor));

        List<VendorOrder> vendorOrders = new ArrayList<>();

        for (Map.Entry<Vendor, List<OrderItem>> entry : grouped.entrySet()) {

            Vendor vendor = entry.getKey();

            List<OrderItem> items = entry.getValue();

            VendorOrder vendorOrder = new VendorOrder();

            vendorOrder.setOrder(order);

            vendorOrder.setVendor(vendor);

            vendorOrder.setVendorOrderStatus(VendorOrderStatus.PENDING);

            vendorOrder.setShippingFee(BigDecimal.ZERO);

            vendorOrder.setDiscount(BigDecimal.ZERO);

            BigDecimal subtotal =
                    items.stream()
                            .map(OrderItem::getTotalPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

            vendorOrder.setSubtotal(subtotal);

            vendorOrder.setTotalPrice(subtotal);

            vendorOrder.setVendorOrderNumber(
                    "VORD-"
                            + System.currentTimeMillis()
                            + "-"
                            + vendor.getId()
            );

            vendorOrders.add(vendorOrder);
        }

        return vendorOrderRepository.saveAll(vendorOrders);
    }



    private List<OrderItem> createOrderItems(Order order, Cart cart) {

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {

            OrderItem item = new OrderItem();

            item.setOrder(order);

            ProductVariant variant = cartItem.getProductVariant();

            if (variant == null) {
                throw new RuntimeException("Product variant not found");
            }

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

        ShippingAddress shippingAddress =
                saveShippingAddress(request, user);

        Order order = createOrder(
                user,
                shippingAddress,
                request.getPaymentMethod()
        );

        order = orderRepository.save(order);

        List<OrderItem> orderItems =
                createOrderItems(order, cart);

        BigDecimal subtotal =
                calculateSubtotal(orderItems);

        order.setSubtotal(subtotal);

        order.setTotalPrice(
                calculateTotalPrice(
                        subtotal,
                        order.getShippingFee(),
                        order.getDiscount()
                )
        );

        order.getPayment().setAmount(order.getTotalPrice());

//        orderRepository.save(order);

        List<VendorOrder> vendorOrders = createVendorOrders(order, orderItems);

        assignVendorOrders(orderItems, vendorOrders);

        reduceStock(orderItems);

        cartService.clearCart();

        return OrderMapper.toResponseDto(order);

    }


    public CartDto getCheckoutSummary() {
        return cartService.getCart();
    }

}
