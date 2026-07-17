package com.abc.multiVendorEProject.service;

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
import com.abc.multiVendorEProject.mapper.OrderMapper;
import com.abc.multiVendorEProject.mapper.ShippingAddressMapper;
import com.abc.multiVendorEProject.repository.OrderRepository;
import com.abc.multiVendorEProject.repository.ShippingAddressRepository;
import com.abc.multiVendorEProject.repository.UserRepository;
import com.abc.multiVendorEProject.repository.VariantRepository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CheckoutService {

    private final CartService cartService;
    private final UserRepository userRepository;
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
        order.setPaymentMethod(paymentMethod);
        order.setPaymentStatus(PaymentStatus.PENDING);

        order.setShippingFee(BigDecimal.ZERO);
        order.setDiscount(BigDecimal.ZERO);

        return order;
    }



    private void createOrderItems(Order order, Cart cart) {

        for (CartItem cartItem : cart.getItems()) {

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);

            ProductVariant variant = cartItem.getProductVariant();

            if (variant.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException(
                        "Insufficient stock for SKU: "
                                + variant.getSku()
                );
            }

            orderItem.setVariant(variant);

            orderItem.setVendor(variant.getProduct().getVendor());

            orderItem.setQuantity(cartItem.getQuantity());

            orderItem.setUnitPrice(cartItem.getUnitPrice());

            orderItem.setTotalPrice(cartItem.getTotalPrice());

            order.getOrderItems().add(orderItem);
        }

    }


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

        createOrderItems(order, cart);

        BigDecimal subtotal = calculateSubtotal(order.getOrderItems());

        order.setSubtotal(subtotal);

        order.setTotalPrice(
                calculateTotalPrice(
                        subtotal,
                        order.getShippingFee(),
                        order.getDiscount()
                )
        );

        reduceStock(order.getOrderItems());

        order = orderRepository.save(order);

        cartService.clearCart();

        return OrderMapper.toResponseDto(order);

    }


    public CartDto getCheckoutSummary() {
        return cartService.getCart();
    }

}
