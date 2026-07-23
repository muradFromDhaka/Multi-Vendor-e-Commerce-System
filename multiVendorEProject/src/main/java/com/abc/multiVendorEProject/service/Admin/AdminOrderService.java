package com.abc.multiVendorEProject.service.Admin;

import com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto.AdminOrderDetailsResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto.AdminOrderListResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.PaymentDto.UpdatePaymentStatusRequestDto;
import com.abc.multiVendorEProject.entity.Order;
import com.abc.multiVendorEProject.entity.OrderItem;
import com.abc.multiVendorEProject.entity.Variant.ProductVariant;
import com.abc.multiVendorEProject.enums.OrderStatus;
import com.abc.multiVendorEProject.enums.PaymentMethod;
import com.abc.multiVendorEProject.enums.PaymentStatus;
import com.abc.multiVendorEProject.enums.VendorOrderStatus;
import com.abc.multiVendorEProject.mapper.OrderMapper;
import com.abc.multiVendorEProject.repository.OrderRepository;
import com.abc.multiVendorEProject.repository.VariantRepository.ProductVariantRepository;
import com.abc.multiVendorEProject.service.OrderService;
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
    private final OrderService orderService;
    private final ProductVariantRepository productVariantRepository;


    //    ===================Helper method========================

    private Order getOrderOrThrow(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));
    }

    private void validateAdminCancel(Order order) {

        if (order.getVendorOrders().isEmpty()) {
            throw new RuntimeException("Vendor orders not found.");
        }

        boolean canCancel = order.getVendorOrders()
                .stream()
                .allMatch(v ->

                        v.getVendorOrderStatus() == VendorOrderStatus.PENDING
                                ||
                                v.getVendorOrderStatus() == VendorOrderStatus.CONFIRMED);

        if (!canCancel) {
            throw new RuntimeException(
                    "Order cannot be cancelled because one or more vendor orders have already started processing.");
        }
    }

    private void restoreStock(Order order) {

        for (OrderItem item : order.getOrderItems()) {

            ProductVariant variant = item.getVariant();

            variant.setStock(
                    variant.getStock() + item.getQuantity());
        }

        productVariantRepository.saveAll(

                order.getOrderItems()
                        .stream()
                        .map(OrderItem::getVariant)
                        .toList());
    }

    private void cancelVendorOrders(Order order) {

        order.getVendorOrders()
                .forEach(v ->

                        v.setVendorOrderStatus(
                                VendorOrderStatus.CANCELLED));
    }


    private void validatePaymentStatusTransition(
            PaymentStatus currentStatus,
            PaymentStatus newStatus) {

        if (currentStatus == newStatus) {
            throw new RuntimeException(
                    "Payment is already " + newStatus);
        }

        switch (currentStatus) {

            case PENDING -> {

                if (newStatus != PaymentStatus.PAID
                        && newStatus != PaymentStatus.FAILED
                        && newStatus != PaymentStatus.CANCELLED) {

                    throw new RuntimeException(
                            "Pending payment can only become PAID, FAILED or CANCELLED.");
                }
            }

            case PAID -> {

                if (newStatus != PaymentStatus.REFUNDED) {

                    throw new RuntimeException(
                            "Paid payment can only be REFUNDED.");
                }
            }

            case FAILED -> {

                if (newStatus != PaymentStatus.PAID
                        && newStatus != PaymentStatus.CANCELLED) {

                    throw new RuntimeException(
                            "Failed payment can only become PAID or CANCELLED.");
                }
            }

            case REFUNDED ->

                    throw new RuntimeException(
                            "Refunded payment cannot be updated.");

            case CANCELLED ->

                    throw new RuntimeException(
                            "Cancelled payment cannot be updated.");
        }
    }

    private void validateOrderAndPaymentStatus(
            OrderStatus orderStatus,
            PaymentMethod paymentMethod,
            PaymentStatus paymentStatus) {

        // ==========================
        // Cancelled Order
        // ==========================
        if (orderStatus == OrderStatus.CANCELLED) {

            if (paymentStatus == PaymentStatus.PAID) {
                throw new RuntimeException(
                        "Cancelled order cannot remain PAID.");
            }

            if (paymentStatus == PaymentStatus.REFUNDED) {

                if (paymentMethod == PaymentMethod.CASH_ON_DELIVERY) {
                    throw new RuntimeException(
                            "COD order cannot be REFUNDED.");
                }
            }
        }

        // ==========================
        // Delivered Order
        // ==========================
        if (orderStatus == OrderStatus.DELIVERED) {

            if (paymentStatus == PaymentStatus.FAILED) {
                throw new RuntimeException(
                        "Delivered order cannot have FAILED payment.");
            }

            if (paymentStatus == PaymentStatus.CANCELLED) {
                throw new RuntimeException(
                        "Delivered order cannot have CANCELLED payment.");
            }

            if (paymentMethod == PaymentMethod.CASH_ON_DELIVERY
                    && paymentStatus != PaymentStatus.PAID) {

                throw new RuntimeException(
                        "Delivered COD order must be PAID.");
            }
        }

        // ==========================
        // Returned Order
        // ==========================
        if (orderStatus == OrderStatus.RETURNED) {

            if (paymentMethod == PaymentMethod.CASH_ON_DELIVERY
                    && paymentStatus == PaymentStatus.REFUNDED) {

                throw new RuntimeException(
                        "Returned COD order cannot be REFUNDED.");
            }

            if (paymentMethod != PaymentMethod.CASH_ON_DELIVERY
                    && paymentStatus == PaymentStatus.CANCELLED) {

                throw new RuntimeException(
                        "Returned online payment should be REFUNDED.");
            }
        }

        // ==========================
        // Pending Order
        // ==========================
        if (orderStatus == OrderStatus.PENDING
                && paymentStatus == PaymentStatus.REFUNDED) {

            throw new RuntimeException(
                    "Pending order cannot be REFUNDED.");
        }
    }


    private void validatePaymentRequest(
            Order order,
            UpdatePaymentStatusRequestDto request) {

        PaymentStatus paymentStatus = request.paymentStatus();

        switch (paymentStatus) {

            case PAID -> {

                if (order.getPayment().getPaymentMethod() != PaymentMethod.CASH_ON_DELIVERY
                        && (request.transactionId() == null
                        || request.transactionId().isBlank())) {

                    throw new RuntimeException(
                            "Transaction Id is required.");
                }
            }

            case REFUNDED -> {

                if (order.getPayment().getPaymentMethod() == PaymentMethod.CASH_ON_DELIVERY) {

                    throw new RuntimeException(
                            "Cash On Delivery order cannot be refunded.");
                }

                if (order.getPayment().getPaymentStatus() != PaymentStatus.PAID) {

                    throw new RuntimeException(
                            "Only PAID payment can be refunded.");
                }

                if (request.refundTransactionId() == null
                        || request.refundTransactionId().isBlank()) {

                    throw new RuntimeException(
                            "Refund Transaction Id is required.");
                }

                if (request.refundedAmount() == null
                        || request.refundedAmount().compareTo(BigDecimal.ZERO) <= 0) {

                    throw new RuntimeException(
                            "Refund amount must be greater than zero.");
                }

                if (request.refundedAmount()
                        .compareTo(order.getTotalPrice()) > 0) {

                    throw new RuntimeException(
                            "Refund amount cannot exceed order total.");
                }
            }

            default -> {
                // Nothing
            }
        }
    }


    private void updatePaymentAuditFields(
            Order order,
            UpdatePaymentStatusRequestDto request) {

        switch (request.paymentStatus()) {

            case PAID -> {

                order.getPayment().setPaidAt(LocalDateTime.now());

                order.getPayment().setTransactionId(
                        request.transactionId());

                order.getPayment().setRefundedAt(null);

                order.getPayment().setRefundTransactionId(null);

                order.getPayment().setRefundedAmount(null);
            }

            case REFUNDED -> {

                order.getPayment().setRefundedAt(LocalDateTime.now());

                order.getPayment().setRefundTransactionId(
                        request.refundTransactionId());

                order.getPayment().setRefundedAmount(
                        request.refundedAmount());
            }

            case FAILED, CANCELLED -> {

                order.getPayment().setPaidAt(null);

                order.getPayment().setTransactionId(null);

                order.getPayment().setRefundedAt(null);

                order.getPayment().setRefundTransactionId(null);

                order.getPayment().setRefundedAmount(null);
            }

            case PENDING -> {

                // Nothing
            }
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
    public AdminOrderDetailsResponseDto updatePaymentStatus(
            Long orderId,
            UpdatePaymentStatusRequestDto request) {

        Order order = getOrderOrThrow(orderId);

        validatePaymentStatusTransition(
                order.getPayment().getPaymentStatus(),
                request.paymentStatus());

        validateOrderAndPaymentStatus(
                order.getOrderStatus(),
                order.getPayment().getPaymentMethod(),
                request.paymentStatus());

        validatePaymentRequest(
                order,
                request);

        order.getPayment().setPaymentStatus(
                request.paymentStatus());

        updatePaymentAuditFields(
                order,
                request);

        Order updatedOrder =
                orderRepository.save(order);

        return OrderMapper.toAdminDetailsDto(updatedOrder);
    }


    @Transactional
    public AdminOrderDetailsResponseDto cancelOrder(Long orderId) {

        Order order = getOrderOrThrow(orderId);

        validateAdminCancel(order);

        restoreStock(order);

        cancelVendorOrders(order);

        // Update Payment Status
        if (order.getPayment().getPaymentStatus() == PaymentStatus.PENDING
                || order.getPayment().getPaymentStatus() == PaymentStatus.FAILED) {

            order.getPayment().setPaymentStatus(PaymentStatus.CANCELLED);
        }

        orderService.updateParentOrderStatus(order.getId());

        Order savedOrder = orderRepository.save(order);

        return OrderMapper.toAdminDetailsDto(savedOrder);
    }

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
        return orderRepository.countByPayment_PaymentStatus(paymentStatus);
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
        Order order = getOrderOrThrow(orderId);

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
