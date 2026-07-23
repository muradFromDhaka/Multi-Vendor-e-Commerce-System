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
    // Revenue Analytics
    // ==============================

    private BigDecimal totalGrossRevenue;
    private BigDecimal todayGrossRevenue;
    private BigDecimal monthlyGrossRevenue;
    private BigDecimal yearlyGrossRevenue;

    private BigDecimal totalNetRevenue;
    private BigDecimal todayNetRevenue;
    private BigDecimal monthlyNetRevenue;
    private BigDecimal yearlyNetRevenue;

    private BigDecimal averagePaymentAmount;
    private BigDecimal highestPaymentAmount;
    private BigDecimal lowestPaymentAmount;


    // ================Sales Analytics==============

    private BigDecimal todaySalesAmount;
    private BigDecimal monthlySalesAmount;
    private BigDecimal yearlySalesAmount;


// ======================Refund Analytics======================

    private BigDecimal totalRefundAmount;
    private BigDecimal todayRefundAmount;
    private BigDecimal monthlyRefundAmount;
    private BigDecimal yearlyRefundAmount;
    private BigDecimal averageRefundAmount;
    private BigDecimal highestRefundAmount;
    private BigDecimal lowestRefundAmount;


    // ==============================
    // Orders Analytics
    // ==============================

    private Long totalOrders;
    private Long pendingOrders;
    private Long processingOrders;
    private Long shippedOrders;
    private Long deliveredOrders;
    private Long cancelledOrders;
    private Long returnedOrders;
    private Long paidOrders;
    private Long todayOrders;


    // ==============================
    // Payment Analytics
    // ==============================

    private Long pendingPayments;
    private Long paidPayments;
    private Long failedPayments;
    private Long refundedPayments;
    private Long cancelledPayments;
    private Long totalPayments;


//    ==============Payment Method Analytics================

//   Payment Method Analytics

    private BigDecimal cardRevenue;
    private BigDecimal cashOnDeliveryRevenue;
    private BigDecimal mobileBankingRevenue;
    private BigDecimal bankTransferRevenue;

//    Transaction Count

    private Long cardPayments;
    private Long cashOnDeliveryPayments;
    private Long mobileBankingPayments;
    private Long bankTransferPayments;

//    Average Payment

    private BigDecimal averageCardPayment;
    private BigDecimal averageCashOnDeliveryPayment;
    private BigDecimal averageMobileBankingPayment;
    private BigDecimal averageBankTransferPayment;


//    =============Payment Provider Analytics===========================

    private BigDecimal manualRevenue;
    private BigDecimal bkashRevenue;
    private BigDecimal nagadRevenue;
    private BigDecimal rocketRevenue;
    private BigDecimal sslCommerzRevenue;
    private BigDecimal stripeRevenue;



//    =====================Finance Analytics======================

    private BigDecimal pendingPaymentAmount;
    private BigDecimal failedPaymentAmount;
    private BigDecimal cancelledPaymentAmount;


    // ==============================
    // Customer Analytics
    // ==============================

    private Long totalCustomers;
    private Long todayCustomers;
    private Long activeCustomers;
    private Long repeatCustomers;

//    ==================Vendor Analytics==================

    private Long totalVendors;
    private Long activeVendors;
    private Long pendingVendors;
    private Long approvedVendors;
    private Long rejectVendors;
    private Long suspendedVendors;
    private Long todayVendors;
    private String topVendorName;
    private BigDecimal topVendorRevenue;


    // ==============================
    // Catalog Analytics
    // ==============================

    private Long totalProducts;
    private Long totalProductVariants;
    private Long totalCategories;
    private Long totalBrands;


// ==============================
// Product Analytics
// ==============================

    private String bestSellingProduct;
    private Long bestSellingProductQuantitySold;
    private BigDecimal bestSellingProductRevenue;

    private String bestSellingCategory;
    private Long bestSellingCategoryQuantitySold;
    private BigDecimal bestSellingCategoryRevenue;


    // ================Inventory Analytics==============

    private Long outOfStockProducts;
    private Long totalStockQuantity;
    private Long lowStockProducts;



//    ============Engagement Analytics=====================

    private Long totalCarts;
    private Long totalReviews;
    private Long totalActiveCarts;
    private BigDecimal averageRating;
    private Long totalWishlistItems;

}
