package com.abc.multiVendorEProject.DTOs.projectDtos.AdminDashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardResponseDto {

    // ==============================
    // Revenue
    // ==============================

    private BigDecimal totalRevenue;

    private BigDecimal todayRevenue;

    private BigDecimal monthlyRevenue;

    private BigDecimal yearlyRevenue;


    // ==============================
    // Orders
    // ==============================

    private Long totalOrders;

    private Long pendingOrders;

    private Long processingOrders;

    private Long shippedOrders;

    private Long deliveredOrders;

    private Long cancelledOrders;

    private Long returnedOrders;

    private Long paidOrders;


    // ==============================
    // Payment
    // ==============================

    private Long pendingPayments;

    private Long paidPayments;

    private Long failedPayments;

    private Long refundedPayments;

    private Long cancelledPayments;


    // ==============================
    // Users
    // ==============================

    private Long totalCustomers;

    private Long totalVendors;

    private Long activeVendors;

    private Long pendingVendors;

    private Long approvedVendors;

    private Long rejectVendors;

    private Long suspendedVendors;


    // ==============================
    // Catalog
    // ==============================

    private Long totalProducts;

    private Long totalProductVariants;

    private Long totalCategories;

    private Long totalBrands;


    // ================Inventory==============

    private Long outOfStockProducts;

    private Long lowStockProducts;

    private Long totalReviews;

    private Long totalWishlistItems;

    private Long totalCarts;

    private BigDecimal averageOrderValue;

    private BigDecimal highestOrderValue;

    private Long todayOrders;

    private Long todayCustomers;

    private Long todayVendors;
}
