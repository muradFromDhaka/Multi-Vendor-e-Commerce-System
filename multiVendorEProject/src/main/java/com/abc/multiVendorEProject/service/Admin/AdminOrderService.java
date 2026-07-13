package com.abc.multiVendorEProject.service.Admin;

import com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto.AdminOrderDetailsResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto.AdminOrderListResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto.UpdateOrderStatusRequestDto;
import com.abc.multiVendorEProject.entity.Order;
import com.abc.multiVendorEProject.enums.OrderStatus;
import com.abc.multiVendorEProject.enums.PaymentStatus;
import com.abc.multiVendorEProject.mapper.OrderMapper;
import com.abc.multiVendorEProject.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RequiredArgsConstructor
@Transactional
@Service
public class AdminOrderService {

    private final OrderRepository orderRepository;


    //    ===================Helper method========================

    private Order getOrderOrThrow(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));
    }

    private void validateStatusTransition(
            OrderStatus current,
            OrderStatus next) {

        switch (current) {

            case PENDING -> {
                if (next != OrderStatus.CONFIRMED &&
                        next != OrderStatus.CANCELLED) {
                    throw new RuntimeException("Invalid status transition.");
                }
            }

            case CONFIRMED -> {
                if (next != OrderStatus.PROCESSING &&
                        next != OrderStatus.CANCELLED) {
                    throw new RuntimeException("Invalid status transition.");
                }
            }

            case PROCESSING -> {
                if (next != OrderStatus.SHIPPED &&
                        next != OrderStatus.CANCELLED) {
                    throw new RuntimeException("Invalid status transition.");
                }
            }

            case SHIPPED -> {
                if (next != OrderStatus.DELIVERED) {
                    throw new RuntimeException("Invalid status transition.");
                }
            }

            case DELIVERED, CANCELLED ->
                    throw new RuntimeException(
                            "Order status can no longer be changed.");
        }
    }


//    ============================Orginal Method===========================
    @Transactional(readOnly = true)
    public Page<AdminOrderListResponseDto> getAllOrders(Pageable pageable) {

        return orderRepository
                .findAllByOrderByCreatedAtDesc(pageable)
                .map(OrderMapper::toAdminListDto);
    }

    @Transactional(readOnly = true)
    public AdminOrderDetailsResponseDto getOrderDetails(Long orderId) {

        Order order = getOrderOrThrow(orderId);

        return OrderMapper.toAdminDetailsDto(order);
    }

    @Transactional
    public AdminOrderDetailsResponseDto updateOrderStatus(
            Long orderId,
            UpdateOrderStatusRequestDto request) {

        Order order = getOrderOrThrow(orderId);

        validateStatusTransition(order.getOrderStatus(), request.orderStatus());
        order.setOrderStatus(request.orderStatus());
        Order updatedOrder = orderRepository.save(order);

        return OrderMapper.toAdminDetailsDto(updatedOrder);
    }


//    ================Revenue=================

    @Transactional(readOnly = true)
    public BigDecimal getRevenueBetween(
            LocalDateTime start,
            LocalDateTime end) {

        return orderRepository.getRevenueBetween(start, end);
    }

    @Transactional(readOnly = true)
    public BigDecimal getTodayRevenue() {

        LocalDate today = LocalDate.now();

        return orderRepository.getRevenueBetween(
                today.atStartOfDay(),
                today.plusDays(1).atStartOfDay().minusNanos(1)
        );
    }

    @Transactional(readOnly = true)
    public BigDecimal getMonthlyRevenue() {

        LocalDate now = LocalDate.now();

        LocalDate start = now.withDayOfMonth(1);
        LocalDate end = start.plusMonths(1).minusDays(1);

        return orderRepository.getRevenueBetween(
                start.atStartOfDay(),
                end.atTime(LocalTime.MAX)
        );
    }

    @Transactional(readOnly = true)
    public BigDecimal getYearlyRevenue() {

        LocalDate now = LocalDate.now();

        LocalDate start = now.withDayOfYear(1);
        LocalDate end = start.plusYears(1).minusDays(1);

        return orderRepository.getRevenueBetween(
                start.atStartOfDay(),
                end.atTime(LocalTime.MAX)
        );
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalRevenue() {
        return orderRepository.getTotalRevenue();
    }


    // ===============================
// Dashboard Statistics
// ===============================

    @Transactional(readOnly = true)
    public long getTotalOrders() {
        return orderRepository.count();
    }

    @Transactional(readOnly = true)
    public long getOrderCountByStatus(OrderStatus orderStatus) {
        return orderRepository.countByOrderStatus(orderStatus);
    }

    @Transactional(readOnly = true)
    public long getPaymentCountByStatus(PaymentStatus paymentStatus) {
        return orderRepository.countByPaymentStatus(paymentStatus);
    }

    @Transactional(readOnly = true)
    public long getOrdersCreatedBetween(
            LocalDateTime start,
            LocalDateTime end) {

        return orderRepository.countByCreatedAtBetween(start, end);
    }


    // =================Search Order==============

    // ===============================
// Search
// ===============================

    @Transactional(readOnly = true)
    public AdminOrderDetailsResponseDto getOrderById(Long orderId) {
//        findOrderById(orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new RuntimeException("Order not found: "+orderId));

        return OrderMapper.toAdminDetailsDto(order);
    }

    @Transactional(readOnly = true)
    public AdminOrderDetailsResponseDto getOrderByOrderNumber(String orderNumber) {

        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() ->
                        new RuntimeException("Order not found."));

        return OrderMapper.toAdminDetailsDto(order);
    }


}
