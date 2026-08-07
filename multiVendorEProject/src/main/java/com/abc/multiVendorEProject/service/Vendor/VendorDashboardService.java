package com.abc.multiVendorEProject.service.Vendor;

import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorDashboard.*;
import com.abc.multiVendorEProject.entity.Review;
import com.abc.multiVendorEProject.entity.Variant.ProductVariant;
import com.abc.multiVendorEProject.entity.Vendor;
import com.abc.multiVendorEProject.entity.VendorOrder;
import com.abc.multiVendorEProject.enums.VendorOrderStatus;
import com.abc.multiVendorEProject.repository.*;
import com.abc.multiVendorEProject.repository.VariantRepository.ProductVariantRepository;
import com.abc.multiVendorEProject.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorDashboardService {

    private final VendorService vendorService;
    private final VendorRepository vendorRepository;
    private final ProductVariantRepository productVariantRepository;
    private final VendorOrderRepository vendorOrderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public VendorDashboardResponseDto getDashboard() {

        Vendor vendor = vendorService.getLoggedInVendor();

        long totalProducts =
                productVariantRepository.countByProductVendor(vendor);

        long totalOrders =
                vendorOrderRepository.countByVendor(vendor);

        List<VendorOrder> orders =
                vendorOrderRepository.findTop5ByVendorOrderByCreatedAtDesc(vendor);

        List<RecentVendorOrderDto> recentOrders =
                orders.stream()
                        .map(order -> RecentVendorOrderDto.builder()
                                .vendorOrderId(order.getId())
                                .orderNumber(order.getVendorOrderNumber())
                                .customerName(order.getOrder().getUser().getUserName()) // আপনার User entity অনুযায়ী পরিবর্তন করুন
                                .totalAmount(order.getTotalPrice())
                                .status(order.getVendorOrderStatus())
                                .orderDate(order.getCreatedAt())
                                .build())
                        .toList();

        List<TopSellingProductDto> topProducts =
                productRepository
                        .findByVendorIdOrderBySoldCountDesc(
                                vendor.getId(),
                                PageRequest.of(0,5)
                        )
                        .stream()
                        .map(product -> TopSellingProductDto.builder()
                                .productId(product.getId())
                                .productName(product.getName())
                                .imageUrl(product.getImageUrls().isEmpty()
                                        ? ""
                                        : product.getImageUrls().get(0))
                                .soldQuantity(product.getSoldCount())
                                .revenue(orderItemRepository.getProductRevenue(product.getId()))
                                .build())
                        .toList();

        List<Review> reviews =
                reviewRepository.findTop5ByProductVendorIdOrderByCreatedAtDesc(
                        vendor.getId()
                );

        List<LatestReviewDto> latestReviews =
                reviews.stream()
                        .map(review -> LatestReviewDto.builder()
                                .customerName(review.getUser().getUserName())
                                .productName(review.getProduct().getName())
                                .rating(review.getRating())
                                .comment(review.getComment())
                                .reviewDate(review.getCreatedAt())
                                .build())
                        .toList();

        long pendingOrders =
                vendorOrderRepository.countByVendorAndVendorOrderStatus(
                        vendor,
                        VendorOrderStatus.PENDING
                );

        long processingOrders =
                vendorOrderRepository.countByVendorAndVendorOrderStatus(
                        vendor,
                        VendorOrderStatus.PROCESSING
                );

        long shippedOrders =
                vendorOrderRepository.countByVendorAndVendorOrderStatus(
                        vendor,
                        VendorOrderStatus.SHIPPED
                );

        long deliveredOrders =
                vendorOrderRepository.countByVendorAndVendorOrderStatus(
                        vendor,
                        VendorOrderStatus.DELIVERED
                );

        long cancelledOrders =
                vendorOrderRepository.countByVendorAndVendorOrderStatus(
                        vendor,
                        VendorOrderStatus.CANCELLED
                );

        List<ProductVariant> lowStockVariants =
                productVariantRepository.findTop5LowStockProducts(
                        vendor.getId(),
                        5,
                        PageRequest.of(0, 5)
                );

        long lowStockProducts =
                productVariantRepository.countByProductVendorAndStockLessThan(
                        vendor,
                        5
                );

        List<LowStockProductDto> lowStockProductsList =
                lowStockVariants.stream()
                        .map(variant -> LowStockProductDto.builder()
                                .variantId(variant.getId())
                                .productName(variant.getProduct().getName())
                                .sku(variant.getSku())
                                .stock(variant.getStock())
                                .build())
                        .toList();



        long outOfStockProducts =
                productVariantRepository.countByProductVendorAndStock(
                        vendor,
                        0
                );

        Long totalReviews =
                reviewRepository.countByVendor(vendor.getId());

        Double averageRating =
                reviewRepository.getAverageRatingByVendor(vendor.getId());

        BigDecimal totalRevenue =
                vendorOrderRepository.getVendorTotalRevenue(vendor);

        BigDecimal todayRevenue =
                vendorOrderRepository.getVendorTodayRevenue(vendor);

        BigDecimal monthlyRevenue =
                vendorOrderRepository.getVendorMonthlyRevenue(vendor);

        return VendorDashboardResponseDto.builder()
                .totalRevenue(totalRevenue)
                .todayRevenue(todayRevenue)
                .monthlyRevenue(monthlyRevenue)

                .totalProducts(totalProducts)

                .totalOrders(totalOrders)
                .pendingOrders(pendingOrders)
                .processingOrders(processingOrders)
                .shippedOrders(shippedOrders)
                .deliveredOrders(deliveredOrders)
                .cancelledOrders(cancelledOrders)

                .lowStockProducts(lowStockProducts)
                .outOfStockProducts(outOfStockProducts)

                .totalReviews(totalReviews)
                .averageRating(averageRating)

                .recentOrders(recentOrders)
                .topSellingProducts(topProducts)
                .lowStockProductsList(lowStockProductsList)
                .latestReviews(latestReviews)

                .build();
    }


    @Transactional(readOnly = true)
    public VendorPerformanceResponseDto getPerformance(
            LocalDate fromDate,
            LocalDate toDate) {

        if (fromDate == null || toDate == null) {
            throw new RuntimeException(
                    "From date and To date are required."
            );
        }

        if (fromDate.isAfter(toDate)) {
            throw new RuntimeException(
                    "From date cannot be after To date."
            );
        }

        Vendor vendor = getLoggedInVendor();

        LocalDateTime fromDateTime =
                fromDate.atStartOfDay();

        LocalDateTime toDateTime =
                toDate.plusDays(1).atStartOfDay();

        BigDecimal revenue =
                vendorOrderRepository.getRevenueByDateRange(
                        vendor,
                        fromDateTime,
                        toDateTime
                );

        Long orders =
                vendorOrderRepository.countOrdersByDateRange(
                        vendor,
                        fromDateTime,
                        toDateTime
                );

        Long productsSold =
                orderItemRepository.getProductsSoldByDateRange(
                        vendor,
                        fromDateTime,
                        toDateTime
                );

        Long newCustomers =
                vendorOrderRepository.countNewCustomersByDateRange(
                        vendor,
                        fromDateTime,
                        toDateTime
                );

        if (revenue == null) {
            revenue = BigDecimal.ZERO;
        }

        if (orders == null) {
            orders = 0L;
        }

        if (productsSold == null) {
            productsSold = 0L;
        }

        if (newCustomers == null) {
            newCustomers = 0L;
        }

        BigDecimal averageOrderValue = BigDecimal.ZERO;

        if (orders > 0) {

            averageOrderValue =
                    revenue.divide(
                            BigDecimal.valueOf(orders),
                            2,
                            RoundingMode.HALF_UP
                    );
        }

        return new VendorPerformanceResponseDto(
                revenue,
                orders,
                productsSold,
                newCustomers,
                averageOrderValue
        );
    }


    private Vendor getLoggedInVendor() {

        String userName = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return vendorRepository.findByUserUserName(userName)
                .orElseThrow(() ->
                        new RuntimeException("Vendor not found."));
    }

//    private enum SoldCountAction {
//        INCREASE,
//        DECREASE
//    }

}