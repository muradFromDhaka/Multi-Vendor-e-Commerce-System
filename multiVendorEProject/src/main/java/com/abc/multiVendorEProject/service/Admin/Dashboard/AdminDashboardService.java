package com.abc.multiVendorEProject.service.Admin.Dashboard;

import com.abc.multiVendorEProject.DTOs.projectDtos.AdminDashboard.AdminDashboardResponseDto;
import com.abc.multiVendorEProject.entity.Category;
import com.abc.multiVendorEProject.entity.Product;
import com.abc.multiVendorEProject.entity.Vendor;
import com.abc.multiVendorEProject.enums.*;
import com.abc.multiVendorEProject.repository.*;
import com.abc.multiVendorEProject.repository.VariantRepository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private final OrderItemRepository orderItemRepository;

    private LocalDateTime todayStart;
    private LocalDateTime todayEnd;
    private LocalDateTime monthStart;
    private LocalDateTime yearStart;

    public AdminDashboardResponseDto getDashboard() {

        initializeDateRange();

        AdminDashboardResponseDto dto = new AdminDashboardResponseDto();

        loadRevenue(dto);
        loadSales(dto);
        loadRefundAnalytics(dto);

        loadOrders(dto);

        loadPayments(dto);
        loadPaymentMethodAnalytics(dto);
        loadPaymentProviderAnalytics(dto);
        loadFinanceAnalytics(dto);

        loadUsers(dto);
        loadVendorAnalytics(dto);

        loadCatalog(dto);
        loadProductAnalytics(dto);

        loadInventory(dto);

        loadEngagement(dto);

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

        dto.setTotalGrossRevenue(
                paymentRepository.getGrossRevenue());

        dto.setTodayGrossRevenue(
                paymentRepository.getGrossRevenueBetween(
                        todayStart,
                        todayEnd));

        dto.setMonthlyGrossRevenue(
                paymentRepository.getGrossRevenueBetween(
                        monthStart,
                        todayEnd));

        dto.setYearlyGrossRevenue(
                paymentRepository.getGrossRevenueBetween(
                        yearStart,
                        todayEnd));

        dto.setTotalNetRevenue(
                paymentRepository.getGrossRevenue()
                        .subtract(paymentRepository.getTotalRefundAmount()));

        dto.setTodayNetRevenue(
                paymentRepository.getGrossRevenueBetween(todayStart, todayEnd)
                        .subtract(paymentRepository.getRefundAmountBetween(todayStart, todayEnd)));

        dto.setMonthlyNetRevenue(
                paymentRepository.getGrossRevenueBetween(monthStart, todayEnd)
                        .subtract(paymentRepository.getRefundAmountBetween(monthStart, todayEnd)));

        dto.setYearlyNetRevenue(
                paymentRepository.getGrossRevenueBetween(yearStart, todayEnd)
                        .subtract(paymentRepository.getRefundAmountBetween(yearStart, todayEnd)));

        dto.setAveragePaymentAmount(
                paymentRepository.getAveragePaymentAmount(
                        PaymentStatus.PAID));

        dto.setHighestPaymentAmount(
                paymentRepository.getHighestPaymentAmount(
                        PaymentStatus.PAID));

        dto.setLowestPaymentAmount(
                paymentRepository.getLowestPaymentAmount(
                        PaymentStatus.PAID));

    }


    //    ==============Sales Analytics=============

    private void loadSales(AdminDashboardResponseDto dto) {

        dto.setTodaySalesAmount(
                paymentRepository.getGrossRevenueBetween(
                        todayStart,
                        todayEnd));

        dto.setMonthlySalesAmount(
                paymentRepository.getGrossRevenueBetween(
                        monthStart,
                        todayEnd));

        dto.setYearlySalesAmount(
                paymentRepository.getGrossRevenueBetween(
                        yearStart,
                        todayEnd));
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

        dto.setLowestRefundAmount(
                paymentRepository.getLowestRefundAmount(
                        PaymentStatus.REFUNDED));

        dto.setHighestRefundAmount(
                paymentRepository.getHighestRefundAmount(PaymentStatus.REFUNDED));
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

        dto.setCashOnDeliveryPayments(
                paymentRepository.countByPaymentMethod(
                        PaymentMethod.CASH_ON_DELIVERY));

        dto.setMobileBankingPayments(
                paymentRepository.countByPaymentMethod(
                        PaymentMethod.MOBILE_BANKING));

        dto.setBankTransferPayments(
                paymentRepository.countByPaymentMethod(
                        PaymentMethod.BANK_TRANSFER));


        // Average Payment

        dto.setAverageCardPayment(
                paymentRepository.getAveragePaymentByMethod(
                        PaymentMethod.CARD));

        dto.setAverageCashOnDeliveryPayment(
                paymentRepository.getAveragePaymentByMethod(
                        PaymentMethod.CASH_ON_DELIVERY));

        dto.setAverageMobileBankingPayment(
                paymentRepository.getAveragePaymentByMethod(
                        PaymentMethod.MOBILE_BANKING));

        dto.setAverageBankTransferPayment(
                paymentRepository.getAveragePaymentByMethod(
                        PaymentMethod.BANK_TRANSFER));

    }


    //    =============Payment Provider Analytics===========================

    private void loadPaymentProviderAnalytics(AdminDashboardResponseDto dto) {

        dto.setManualRevenue(
                paymentRepository.getAveragePaymentByProvider(
                        PaymentProvider.MANUAL));

        dto.setBkashRevenue(
                paymentRepository.getAveragePaymentByProvider(
                        PaymentProvider.BKASH));

        dto.setNagadRevenue(
                paymentRepository.getAveragePaymentByProvider(
                        PaymentProvider.NAGAD));

        dto.setRocketRevenue(
                paymentRepository.getAveragePaymentByProvider(
                        PaymentProvider.ROCKET));

        dto.setSslCommerzRevenue(
                paymentRepository.getAveragePaymentByProvider(
                        PaymentProvider.SSLCOMMERZ));

        dto.setStripeRevenue(
                paymentRepository.getAveragePaymentByProvider(
                        PaymentProvider.STRIPE));
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

        dto.setActiveCustomers(
                userRepository.countByRolesRoleNameAndEnabledTrueAndDeletedFalse(
                        "ROLE_USER"));

        dto.setRepeatCustomers(
                orderRepository.countRepeatCustomers());

    }


//    ==============Vendor Analytics=================

    private void loadVendorAnalytics(AdminDashboardResponseDto dto) {

        Vendor topVendor = orderItemRepository
                .findTopVendorsByRevenue(PageRequest.of(0, 1))
                .stream()
                .findFirst()
                .orElse(null);

        if (topVendor != null) {

            dto.setTopVendorName(topVendor.getShopName());

            dto.setTopVendorRevenue(
                    orderItemRepository.getVendorRevenue(topVendor.getId()));
        }

        if (topVendor != null) {

            dto.setTopVendorName(
                    topVendor.getShopName());

            dto.setTopVendorRevenue(
                    orderItemRepository.getVendorRevenue(
                            topVendor.getId()));
        }
    }

//    =================Product Analytics=================

    private void loadProductAnalytics(AdminDashboardResponseDto dto) {

        Product product =
                productRepository.findTopByDeletedFalseOrderBySoldCountDesc()
                        .orElse(null);

        if (product != null) {

            dto.setBestSellingProduct(
                    product.getName());

            dto.setBestSellingProductQuantitySold(
                    product.getSoldCount());

            dto.setBestSellingProductRevenue(
                    orderItemRepository.getProductRevenue(
                            product.getId()));
        }

        List<Category> categories = orderItemRepository.findBestSellingCategories();

        Category category = categories.isEmpty()
                ? null
                : categories.get(0);

        if (category != null) {

            dto.setBestSellingCategory(
                    category.getName());

            dto.setBestSellingCategoryQuantitySold(
                    orderItemRepository.getCategoryQuantitySold(
                            category.getId()));

            dto.setBestSellingCategoryRevenue(
                    orderItemRepository.getCategoryRevenue(
                            category.getId()));
        }
    }



    // =====================================================
    // Catalog Analytics
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
    // Inventory Analytics
    // =====================================================

    private void loadInventory(AdminDashboardResponseDto dto) {

        dto.setOutOfStockProducts(
                productVariantRepository.countByStock(0));

        dto.setLowStockProducts(
                productVariantRepository.countByStockLessThan(
                        LOW_STOCK_LIMIT));

        dto.setOutOfStockProducts(
                productVariantRepository.countByStock(0));

        dto.setTotalStockQuantity(
                productVariantRepository.getTotalStockQuantity());

        dto.setLowStockProducts(
                productVariantRepository.countByStockBetween(
                        1,
                        LOW_STOCK_LIMIT - 1));
    }

    // =====================================================
    // Engagement Analytics
    // =====================================================

    private void loadEngagement(AdminDashboardResponseDto dto) {

        dto.setTotalReviews(
                reviewRepository.count());

        dto.setAverageRating(
                reviewRepository.getAverageRating());

        dto.setTotalWishlistItems(
                wishlistRepository.getTotalWishlistItems());

        dto.setTotalCarts(
                cartRepository.count());

        dto.setTotalActiveCarts(
                cartRepository.countActiveCarts());
    }

}