package com.abc.multiVendorEProject.service.Admin;

import com.abc.multiVendorEProject.DTOs.projectDtos.AdminDashboard.AdminDashboardResponseDto;
import com.abc.multiVendorEProject.enums.OrderStatus;
import com.abc.multiVendorEProject.enums.PaymentMethod;
import com.abc.multiVendorEProject.enums.PaymentStatus;
import com.abc.multiVendorEProject.enums.VendorStatus;
import com.abc.multiVendorEProject.repository.*;
import com.abc.multiVendorEProject.repository.VariantRepository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminDashboardService {

    private static final int LOW_STOCK_LIMIT = 10;

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final VendorRepository vendorRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final WishlistRepository wishlistRepository;
    private final CartRepository cartRepository;
    private final PaymentRepository paymentRepository;

    private LocalDateTime todayStart;
    private LocalDateTime todayEnd;
    private LocalDateTime monthStart;
    private LocalDateTime yearStart;

    public AdminDashboardResponseDto getDashboard() {

        initializeDateRange();

        AdminDashboardResponseDto dto = new AdminDashboardResponseDto();

        loadRevenue(dto);
        loadOrders(dto);
        loadPayments(dto);
        loadUsers(dto);
        loadCatalog(dto);
        loadInventory(dto);
        loadOthers(dto);

        return dto;
    }

    // =====================================================
    // Date Range
    // =====================================================

    private void initializeDateRange() {

        LocalDate today = LocalDate.now();

        todayStart = today.atStartOfDay();
        todayEnd = today.plusDays(1).atStartOfDay();

        monthStart = today.withDayOfMonth(1).atStartOfDay();
        yearStart = today.withDayOfYear(1).atStartOfDay();
    }


    // =====================================================
    // Revenue
    // =====================================================

    private void loadRevenue(AdminDashboardResponseDto dto) {

        dto.setTotalRevenue(
                paymentRepository.getGrossRevenue());

        dto.setTodayRevenue(
                paymentRepository.getGrossRevenueBetween(
                        todayStart,
                        todayEnd));

        dto.setMonthlyRevenue(
                paymentRepository.getRevenueBetween(
                        monthStart,
                        todayEnd));

        dto.setYearlyRevenue(
                paymentRepository.getRevenueBetween(
                        yearStart,
                        todayEnd));

        dto.setAveragePaymentAmount(
                paymentRepository.getAveragePaymentAmount(PaymentStatus.PAID));

        dto.setHighestPaymentAmount(
                paymentRepository.getHighestPaymentAmount(PaymentStatus.PAID));

        dto.setLowestPaymentAmount(
                paymentRepository.getLowestPaymentAmount(PaymentStatus.PAID));


    }

//    ==============Refund Analytics=============

    private void loadRefundAnalytics(AdminDashboardResponseDto dto) {

        dto.setTotalRefundAmount(
                paymentRepository.getTotalRefundAmount());

        dto.setTodayRefundAmount(
                paymentRepository.getRefundAmountBetween(
                        todayStart,
                        todayEnd));

        dto.setMonthlyRefundAmount(
                paymentRepository.getRefundAmountBetween(
                        monthStart,
                        todayEnd));

        dto.setYearlyRefundAmount(
                paymentRepository.getRefundAmountBetween(
                        yearStart,
                        todayEnd));

        dto.setAverageRefundAmount(
                paymentRepository.getAverageRefundAmount(PaymentStatus.REFUNDED));

        dto.setHighestRefundAmount(
                paymentRepository.getHighestRefundAmount());
    }


    // ================Payment Method Analytics===============

    private void loadPaymentMethodAnalytics(AdminDashboardResponseDto dto) {

        // Revenue

        dto.setCardRevenue(
                paymentRepository.getRevenueByPaymentMethod(
                        PaymentMethod.CARD));

        dto.setCashOnDeliveryRevenue(
                paymentRepository.getRevenueByPaymentMethod(
                        PaymentMethod.CASH_ON_DELIVERY));

        dto.setMobileBankingRevenue(
                paymentRepository.getRevenueByPaymentMethod(
                        PaymentMethod.MOBILE_BANKING));

        dto.setBankTransferRevenue(
                paymentRepository.getRevenueByPaymentMethod(
                        PaymentMethod.BANK_TRANSFER));


        // Transaction Count

        dto.setCardPayments(
                paymentRepository.countByPaymentMethod(
                        PaymentMethod.CARD));

        dto.setCodPayments(
                paymentRepository.countByPaymentMethod(
                        PaymentMethod.CASH_ON_DELIVERY));

        dto.setBkashPayments(
                paymentRepository.countByPaymentMethod(
                        PaymentMethod.MOBILE_BANKING));

        dto.setNagadPayments(
                paymentRepository.countByPaymentMethod(
                        PaymentMethod.BANK_TRANSFER));


        // Average Payment

        dto.setAverageBkashPayment(
                paymentRepository.getAveragePaymentByMethod(
                        PaymentMethod.CARD));

        dto.setAverageCodPayment(
                paymentRepository.getAveragePaymentByMethod(
                        PaymentMethod.CASH_ON_DELIVERY));

        dto.setAverageNagadPayment(
                paymentRepository.getAveragePaymentByMethod(
                        PaymentMethod.MOBILE_BANKING));

        dto.setAverageCardPayment(
                paymentRepository.getAveragePaymentByMethod(
                        PaymentMethod.BANK_TRANSFER));

    }


    // ====================Finance Analytics===================

    private void loadFinanceAnalytics(AdminDashboardResponseDto dto) {

        dto.setPendingPaymentAmount(
                paymentRepository.getPendingPaymentAmount());

        dto.setFailedPaymentAmount(
                paymentRepository.getFailedPaymentAmount());

        dto.setCancelledPaymentAmount(
                paymentRepository.getCancelledPaymentAmount());
    }


    // =====================================================
    // Orders
    // =====================================================

    private void loadOrders(AdminDashboardResponseDto dto) {

        dto.setTotalOrders(orderRepository.count());

        dto.setPendingOrders(
                orderRepository.countByOrderStatus(OrderStatus.PENDING));

        dto.setProcessingOrders(
                orderRepository.countByOrderStatus(OrderStatus.PROCESSING));

        dto.setShippedOrders(
                orderRepository.countByOrderStatus(OrderStatus.SHIPPED));

        dto.setDeliveredOrders(
                orderRepository.countByOrderStatus(OrderStatus.DELIVERED));

        dto.setCancelledOrders(
                orderRepository.countByOrderStatus(OrderStatus.CANCELLED));

        dto.setReturnedOrders(
                orderRepository.countByOrderStatus(OrderStatus.RETURNED));

        dto.setTodayOrders(
                orderRepository.countByCreatedAtBetween(
                        todayStart,
                        todayEnd));

        dto.setPaidOrders(
                orderRepository.countByPayment_PaymentStatus(
                        PaymentStatus.PAID));
    }

    // =====================================================
    // Payments
    // =====================================================

    private void loadPayments(AdminDashboardResponseDto dto) {

        dto.setPendingPayments(
                paymentRepository.countByPaymentStatus(
                        PaymentStatus.PENDING));

        dto.setPaidPayments(
                paymentRepository.countByPaymentStatus(
                        PaymentStatus.PAID));

        dto.setFailedPayments(
                paymentRepository.countByPaymentStatus(
                        PaymentStatus.FAILED));

        dto.setRefundedPayments(
                paymentRepository.countByPaymentStatus(
                        PaymentStatus.REFUNDED));

        dto.setCancelledPayments(
                paymentRepository.countByPaymentStatus(
                        PaymentStatus.CANCELLED));

        dto.setTotalPayments(
                paymentRepository.count());
    }

    // =====================================================
    // Users
    // =====================================================

    private void loadUsers(AdminDashboardResponseDto dto) {

        dto.setTotalCustomers(
                userRepository.countByRolesRoleNameAndDeletedFalse("ROLE_USER"));

        dto.setTodayCustomers(
                userRepository.countByRolesAndDateCreatedBetween(
                        "ROLE_USER",
                        todayStart,
                        todayEnd));

        dto.setTotalVendors(
                vendorRepository.count());

        dto.setApprovedVendors(
                vendorRepository.countByStatus(
                        VendorStatus.APPROVED));

        dto.setActiveVendors(
                vendorRepository.countByStatus(
                        VendorStatus.ACTIVE));

        dto.setPendingVendors(
                vendorRepository.countByStatus(
                        VendorStatus.PENDING));

        dto.setRejectVendors(
                vendorRepository.countByStatus(
                        VendorStatus.REJECTED));

        dto.setSuspendedVendors(
                vendorRepository.countByStatus(
                        VendorStatus.SUSPENDED));

        dto.setTodayVendors(
                vendorRepository.countByCreatedAtBetween(
                        todayStart,
                        todayEnd));
    }

    // =====================================================
    // Catalog
    // =====================================================

    private void loadCatalog(AdminDashboardResponseDto dto) {

        dto.setTotalProducts(
                productRepository.count());

        dto.setTotalProductVariants(
                productVariantRepository.count());

        dto.setTotalCategories(
                categoryRepository.count());

        dto.setTotalBrands(
                brandRepository.count());
    }

    // =====================================================
    // Inventory
    // =====================================================

    private void loadInventory(AdminDashboardResponseDto dto) {

        dto.setOutOfStockProducts(
                productVariantRepository.countByStock(0));

        dto.setLowStockProducts(
                productVariantRepository.countByStockLessThan(
                        LOW_STOCK_LIMIT));
    }

    // =====================================================
    // Others
    // =====================================================

    private void loadOthers(AdminDashboardResponseDto dto) {

        dto.setTotalReviews(
                reviewRepository.count());

        dto.setTotalWishlistItems(
                wishlistRepository.count());

        dto.setTotalCarts(
                cartRepository.count());
    }

}