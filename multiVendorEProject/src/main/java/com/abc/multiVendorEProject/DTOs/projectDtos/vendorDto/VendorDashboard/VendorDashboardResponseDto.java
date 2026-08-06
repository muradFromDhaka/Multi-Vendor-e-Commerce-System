package com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorDashboard;


import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorDashboardResponseDto {

    // Revenue
    private BigDecimal totalRevenue;
    private BigDecimal monthlyRevenue;
    private BigDecimal todayRevenue;

    // Orders
    private Long totalOrders;
    private Long pendingOrders;
    private Long processingOrders;
    private Long shippedOrders;
    private Long deliveredOrders;
    private Long cancelledOrders;

    // Products
    private Long totalProducts;
    private Long lowStockProducts;
    private Long outOfStockProducts;

    // Customers
    private Long totalCustomers;

    // Reviews
    private Long totalReviews;
    private Double averageRating;


    private List<RecentVendorOrderDto> recentOrders;

    private List<TopSellingProductDto> topSellingProducts;

    private List<LowStockProductDto> lowStockProductsList;

    private List<LatestReviewDto> latestReviews;

}
