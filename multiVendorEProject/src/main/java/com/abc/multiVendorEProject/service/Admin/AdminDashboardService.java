package com.abc.multiVendorEProject.service.Admin;

import com.abc.multiVendorEProject.DTOs.projectDtos.AdminDashboard.AdminDashboardResponseDto;
import com.abc.multiVendorEProject.enums.OrderStatus;
import com.abc.multiVendorEProject.enums.PaymentStatus;
import com.abc.multiVendorEProject.enums.VendorStatus;
import com.abc.multiVendorEProject.repository.*;
import com.abc.multiVendorEProject.repository.VariantRepository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
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
                orderRepository.getTotalRevenue());

        dto.setTodayRevenue(
                orderRepository.getRevenueBetween(
                        todayStart,
                        todayEnd));

        dto.setMonthlyRevenue(
                orderRepository.getRevenueBetween(
                        monthStart,
                        todayEnd));

        dto.setYearlyRevenue(
                orderRepository.getRevenueBetween(
                        yearStart,
                        todayEnd));

        dto.setAverageOrderValue(
                orderRepository.getAverageOrderValue(PaymentStatus.PAID));

        dto.setHighestOrderValue(
                orderRepository.getHighestOrderValue(PaymentStatus.PAID));
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

        dto.setPaidOrders(
                orderRepository.countByOrderStatus(OrderStatus.PAID));

        dto.setTodayOrders(
                orderRepository.countByCreatedAtBetween(
                        todayStart,
                        todayEnd));
    }

    // =====================================================
    // Payments
    // =====================================================

    private void loadPayments(AdminDashboardResponseDto dto) {

        dto.setPendingPayments(
                orderRepository.countByPaymentStatus(
                        PaymentStatus.PENDING));

        dto.setPaidPayments(
                orderRepository.countByPaymentStatus(
                        PaymentStatus.PAID));

        dto.setFailedPayments(
                orderRepository.countByPaymentStatus(
                        PaymentStatus.FAILED));

        dto.setRefundedPayments(
                orderRepository.countByPaymentStatus(
                        PaymentStatus.REFUNDED));

        dto.setCancelledPayments(
                orderRepository.countByPaymentStatus(
                        PaymentStatus.CANCELLED));
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