package com.abc.multiVendorEProject.service;

import com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto.OrderRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto.OrderResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.OrderItemRequestDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.ShippingAddressRequestDto;
import com.abc.multiVendorEProject.entity.*;
import com.abc.multiVendorEProject.entity.Variant.ProductVariant;
import com.abc.multiVendorEProject.enums.OrderStatus;
import com.abc.multiVendorEProject.enums.PaymentStatus;
import com.abc.multiVendorEProject.mapper.OrderMapper;
import com.abc.multiVendorEProject.mapper.ShippingAddressMapper;
import com.abc.multiVendorEProject.repository.OrderItemRepository;
import com.abc.multiVendorEProject.repository.OrderRepository;
import com.abc.multiVendorEProject.repository.ProductRepository;
import com.abc.multiVendorEProject.repository.UserRepository;
import com.abc.multiVendorEProject.repository.VariantRepository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final VendorService vendorService;

//    ===================Helper method========================

    private User getLoggedInUser() {

        String userName = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findById(userName)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    private Vendor getLoggedInVendor() {
        return vendorService.getLoggedInVendor();
    }

//    private Order getOrderOrThrow(Long orderId) {
//
////        log.info("Searching Order ID = {}", orderId);
////
////        boolean exists = orderRepository.existsById(orderId);
////
////        log.info("Exists = {}", exists);
//
//        System.out.println("Searching Order ID = " + orderId);
//        System.out.println(orderRepository.existsById(orderId));
//
//        return orderRepository.findById(orderId)
//                .orElseThrow(() ->
//                        new RuntimeException("Order not found"));
//    }


private Order getOrderOrThrow(Long orderId) {

    System.out.println("Searching Order ID = " + orderId);

    boolean exists = orderRepository.existsById(orderId);
    System.out.println("Exists = " + exists);

    Optional<Order> optional = orderRepository.findById(orderId);

    System.out.println("Optional Present = " + optional.isPresent());

    if (optional.isPresent()) {
        Order order = optional.get();
        System.out.println("Loaded Order Id = " + order.getId());
        System.out.println("Order Number = " + order.getOrderNumber());
        return order;
    }

    throw new RuntimeException("Order not found");
}

    private String generateOrderNumber() {

        String orderNumber;

        do {
            orderNumber = "ORD-" + System.currentTimeMillis();
        } while (orderRepository.existsByOrderNumber(orderNumber));

        return orderNumber;
    }

    private ShippingAddress buildShippingAddress(
            ShippingAddressRequestDto request) {

        return ShippingAddressMapper.toEntity(request);
    }


    private List<OrderItem> buildOrderItems(
            Order order,
            List<OrderItemRequestDTO> requests) {

        List<ProductVariant> variants = productVariantRepository.findAllById(
                requests.stream()
                        .map(OrderItemRequestDTO::getVariantId)
                        .toList()
        );

        Map<Long, ProductVariant> variantMap = variants.stream()
                .collect(Collectors.toMap(
                        ProductVariant::getId,
                        Function.identity()
                ));

        return requests.stream()
                .map(item -> {

                    ProductVariant variant = variantMap.get(item.getVariantId());

                    if (variant == null) {
                        throw new RuntimeException(
                                "Variant not found : " + item.getVariantId());
                    }

                    if (variant.getStock() < item.getQuantity()) {
                        throw new RuntimeException(
                                "Insufficient stock for SKU : "
                                        + variant.getSku());
                    }

                    BigDecimal unitPrice =
                            variant.getDiscountPrice() != null
                                    ? variant.getDiscountPrice()
                                    : variant.getPrice();

                    BigDecimal totalPrice = unitPrice.multiply(
                            BigDecimal.valueOf(item.getQuantity()));

                    OrderItem orderItem = new OrderItem();

                    orderItem.setOrder(order);
                    orderItem.setVariant(variant);
                    orderItem.setVendor(variant.getProduct().getVendor());
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setUnitPrice(unitPrice);
                    orderItem.setTotalPrice(totalPrice);

                    return orderItem;
                })
                .toList();
    }

    private BigDecimal calculateSubtotal(
            List<OrderItem> orderItems) {

        return orderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
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

    private BigDecimal calculateTotalPrice(
            BigDecimal subtotal,
            BigDecimal shippingFee,
            BigDecimal discount) {

        return subtotal
                .add(shippingFee)
                .subtract(discount);
    }


//    ============================ Original Method============================

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto request) {

        // Logged-in Customer
        User user = getLoggedInUser();

        // Create Order
        Order order = new Order();
        order.setUser(user);
        order.setOrderNumber(generateOrderNumber());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setPaymentMethod(request.getPaymentMethod());

        // Shipping Address
        ShippingAddress shippingAddress =
                buildShippingAddress(request.getShippingAddress());

        order.setShippingAddress(shippingAddress);

        // Build Order Items
        List<OrderItem> orderItems =
                buildOrderItems(order, request.getItems());

        order.setOrderItems(orderItems);

        // Price Calculation
        BigDecimal subtotal = calculateSubtotal(orderItems);

        BigDecimal shippingFee = BigDecimal.ZERO;
        BigDecimal discount = BigDecimal.ZERO;

        BigDecimal totalPrice =
                calculateTotalPrice(
                        subtotal,
                        shippingFee,
                        discount
                );

        order.setSubtotal(subtotal);
        order.setShippingFee(shippingFee);
        order.setDiscount(discount);
        order.setTotalPrice(totalPrice);

        // Reduce Product Stock
        reduceStock(orderItems);

        // Save Order
        Order savedOrder = orderRepository.save(order);
        return OrderMapper.toResponseDto(savedOrder);
    }


    @Transactional
    public OrderResponseDto cancelMyOrder(Long orderId) {

        User user = getLoggedInUser();

        Order order = getOrderOrThrow(orderId);

        if (!order.getUser().getUserName().equals(user.getUserName())) {
            throw new RuntimeException("You are not authorized to cancel this order.");
        }

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Only pending orders can be cancelled.");
        }

        // Restore Stock
        for (OrderItem item : order.getOrderItems()) {

            ProductVariant variant = item.getVariant();

            variant.setStock(
                    variant.getStock() + item.getQuantity()
            );
        }

        productVariantRepository.saveAll(
                order.getOrderItems()
                        .stream()
                        .map(OrderItem::getVariant)
                        .toList()
        );

        order.setOrderStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);

        return OrderMapper.toResponseDto(order);
    }


    @Transactional(readOnly = true)
    public OrderResponseDto getMyOrder(Long orderId) {

        User user = getLoggedInUser();

        Order order = getOrderOrThrow(orderId);

        if (!order.getUser().getUserName().equals(user.getUserName())) {
            throw new RuntimeException("You are not authorized to view this order.");
        }

        return OrderMapper.toResponseDto(order);
    }


    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getMyOrders(Pageable pageable) {

        User user = getLoggedInUser();

        return orderRepository
                .findByUser(user, pageable)
                .map(OrderMapper::toResponseDto);
    }


    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getMyOrdersByStatus(
            OrderStatus status,
            Pageable pageable) {

        User user = getLoggedInUser();

        Page<Order> orders;

        if (status == null) {
            orders = orderRepository.findByUser(user, pageable);
        } else {
            orders = orderRepository.findByUserAndOrderStatus(user, status, pageable);
        }

        return orders.map(OrderMapper::toResponseDto);
    }


    @Transactional(readOnly = true)
    public boolean hasPurchasedProduct(Long productId) {

        User user = getLoggedInUser();

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found."));

        return orderItemRepository.hasPurchasedProduct(user, product);
    }



}
