package com.abc.multiVendorEProject.service;

import com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto.OrderResponseDto;
import com.abc.multiVendorEProject.entity.*;
import com.abc.multiVendorEProject.entity.Variant.ProductVariant;
import com.abc.multiVendorEProject.enums.OrderStatus;
import com.abc.multiVendorEProject.enums.PaymentStatus;
import com.abc.multiVendorEProject.enums.VendorOrderStatus;
import com.abc.multiVendorEProject.mapper.OrderMapper;
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

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService {

//    private final VendorService vendorService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductVariantRepository productVariantRepository;


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

    private Order getOrderOrThrow(Long orderId){

        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));
    }

    private boolean areAllVendorOrdersPending(Order order) {

        if (order.getVendorOrders().isEmpty()) {
            throw new IllegalStateException(
                    "Order has no vendor orders.");
        }

        return order.getVendorOrders()
                .stream()
                .allMatch(v ->
                        v.getVendorOrderStatus() == VendorOrderStatus.PENDING);
    }


    private void cancelVendorOrders(Order order) {

        order.getVendorOrders()
                .forEach(vendorOrder ->
                        vendorOrder.setVendorOrderStatus(
                                VendorOrderStatus.CANCELLED));
    }


//    ============================ Original Method============================

    @Transactional
    public OrderResponseDto cancelMyOrder(Long orderId) {

        User user = getLoggedInUser();

        Order order = getOrderOrThrow(orderId);

        if (!order.getUser().getUserName().equals(user.getUserName())) {
            throw new RuntimeException("You are not authorized to cancel this order.");
        }

        if (!areAllVendorOrdersPending(order)) {
            throw new RuntimeException(
                    "Order cannot be cancelled because one or more vendors have already started processing it.");
        }

        // Restore Stock
        for (OrderItem item : order.getOrderItems()) {

            ProductVariant variant = item.getVariant();

            variant.setStock(
                    variant.getStock() + item.getQuantity()
            );
        }

        cancelVendorOrders(order);

        productVariantRepository.saveAll(
                order.getOrderItems()
                        .stream()
                        .map(OrderItem::getVariant)
                        .toList()
        );

        order.setOrderStatus(OrderStatus.CANCELLED);

        Payment payment = order.getPayment();

        if (payment != null) {

            if (payment.getPaymentStatus() == PaymentStatus.PENDING
                    || payment.getPaymentStatus() == PaymentStatus.FAILED) {

                payment.setPaymentStatus(PaymentStatus.CANCELLED);
            }
        }

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


    @Transactional
    public void updateParentOrderStatus(Long orderId) {

        Order order = getOrderOrThrow(orderId);

        List<VendorOrder> vendorOrders = order.getVendorOrders();

        if (vendorOrders.isEmpty()) {

            throw new IllegalStateException(
                    "No vendor orders found.");
        }

        int total = vendorOrders.size();

        int pendingCount = 0;
        int confirmedCount = 0;
        int processingCount = 0;
        int packedCount = 0;
        int shippedCount = 0;
        int deliveredCount = 0;
        int cancelledCount = 0;
        int returnedCount = 0;

        for (VendorOrder vendorOrder : vendorOrders) {

            switch (vendorOrder.getVendorOrderStatus()) {

                case PENDING -> pendingCount++;

                case CONFIRMED -> confirmedCount++;

                case PROCESSING -> processingCount++;

                case PACKED -> packedCount++;

                case SHIPPED -> shippedCount++;

                case DELIVERED -> deliveredCount++;

                case CANCELLED -> cancelledCount++;

                case RETURNED -> returnedCount++;
            }
        }

        // এখান থেকে Parent Status Calculate হবে

        OrderStatus newStatus;

        if (cancelledCount == total) {

            newStatus = OrderStatus.CANCELLED;

            if (newStatus == OrderStatus.CANCELLED) {

                Payment payment = order.getPayment();

                if (payment != null &&
                        (payment.getPaymentStatus() == PaymentStatus.PENDING
                                || payment.getPaymentStatus() == PaymentStatus.FAILED)) {

                    payment.setPaymentStatus(PaymentStatus.CANCELLED);
                }
            }

        }else if (deliveredCount == total) {

            newStatus = OrderStatus.DELIVERED;

        }else if (returnedCount == total) {

            newStatus = OrderStatus.RETURNED;

        }else if (deliveredCount > 0) {

            newStatus = OrderStatus.PARTIALLY_DELIVERED;

        }else if (shippedCount > 0) {

            newStatus = OrderStatus.SHIPPED;

        }else if (confirmedCount > 0
                || processingCount > 0
                || packedCount > 0) {

            newStatus = OrderStatus.PROCESSING;

        }else if (cancelledCount > 0) {

            newStatus = OrderStatus.PARTIALLY_CANCELLED;

        }else {

            newStatus = OrderStatus.PENDING;
        }


        // Save Only If Changed
        if (order.getOrderStatus() != newStatus) {

            order.setOrderStatus(newStatus);

            System.out.println("New Status ============================= " + newStatus);

            orderRepository.save(order);
        }




    }



}
