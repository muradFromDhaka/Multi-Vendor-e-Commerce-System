package com.abc.multiVendorEProject.Controller.Admin;

import com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto.AdminOrderDetailsResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto.AdminOrderListResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto.UpdateOrderStatusRequestDto;
import com.abc.multiVendorEProject.enums.OrderStatus;
import com.abc.multiVendorEProject.enums.PaymentStatus;
import com.abc.multiVendorEProject.service.Admin.AdminOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    // =====================================================
    // Order Management
    // =====================================================

    @GetMapping
    public Page<AdminOrderListResponseDto> getAllOrders(Pageable pageable) {
        return adminOrderService.getAllOrders(pageable);
    }

    @GetMapping("/{orderId}")
    public AdminOrderDetailsResponseDto getOrderDetails(
            @PathVariable Long orderId) {

        return adminOrderService.getOrderDetails(orderId);
    }

    @PatchMapping("/{orderId}/status")
    public AdminOrderDetailsResponseDto updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody UpdateOrderStatusRequestDto request) {

        return adminOrderService.updateOrderStatus(orderId, request);
    }

    // =====================================================
    // Search
    // =====================================================

    @GetMapping("/search/{orderId}")
    public AdminOrderDetailsResponseDto getOrderById(
            @PathVariable Long orderId) {

        return adminOrderService.getOrderById(orderId);
    }

    @GetMapping("/search/order-number/{orderNumber}")
    public AdminOrderDetailsResponseDto getOrderByOrderNumber(
            @PathVariable String orderNumber) {

        return adminOrderService.getOrderByOrderNumber(orderNumber);
    }

    // =====================================================
    // Revenue
    // =====================================================

    @GetMapping("/revenue/total")
    public BigDecimal getTotalRevenue() {
        return adminOrderService.getTotalRevenue();
    }

    @GetMapping("/revenue/today")
    public BigDecimal getTodayRevenue() {
        return adminOrderService.getTodayRevenue();
    }

    @GetMapping("/revenue/month")
    public BigDecimal getMonthlyRevenue() {
        return adminOrderService.getMonthlyRevenue();
    }

    @GetMapping("/revenue/year")
    public BigDecimal getYearlyRevenue() {
        return adminOrderService.getYearlyRevenue();
    }

    @GetMapping("/revenue")
    public BigDecimal getRevenueBetween(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end) {

        return adminOrderService.getRevenueBetween(start, end);
    }

    // =====================================================
    // Dashboard Statistics
    // =====================================================

    @GetMapping("/statistics/total-orders")
    public long getTotalOrders() {
        return adminOrderService.getTotalOrders();
    }

    @GetMapping("/statistics/order-status")
    public long getOrderCountByStatus(
            @RequestParam OrderStatus status) {

        return adminOrderService.getOrderCountByStatus(status);
    }

    @GetMapping("/statistics/payment-status")
    public long getPaymentCountByStatus(
            @RequestParam PaymentStatus status) {

        return adminOrderService.getPaymentCountByStatus(status);
    }

    @GetMapping("/statistics/orders-between")
    public long getOrdersCreatedBetween(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end) {

        return adminOrderService.getOrdersCreatedBetween(start, end);
    }

}