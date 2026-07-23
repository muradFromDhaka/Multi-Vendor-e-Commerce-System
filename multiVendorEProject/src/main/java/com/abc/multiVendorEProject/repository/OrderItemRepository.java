package com.abc.multiVendorEProject.repository;

import com.abc.multiVendorEProject.entity.*;
import com.abc.multiVendorEProject.entity.Variant.ProductVariant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {


    List<OrderItem> findByVendor(Vendor vendor);


    List<OrderItem> findByVariant(ProductVariant variant);


    List<OrderItem> findByOrderId(Long orderId);


    Long countByVariant_Product(Product product);


    List<OrderItem> findByVendorIdAndOrderId(Long vendorId, Long orderId);

    Page<OrderItem> findByVariant_Id(Long variantId, Pageable pageable);

    @Query("SELECT DISTINCT oi.order FROM OrderItem oi WHERE oi.vendor.id = :vendorId")
    Page<Order> findOrdersByVendorId(@Param("vendorId") Long vendorId, Pageable pageable);

    @Query("""
    SELECT COUNT(DISTINCT oi.order.id)
    FROM OrderItem oi
    WHERE oi.vendor.id = :vendorId
     """)
    long countOrders(@Param("vendorId")Long vendorId);

    @Query("""
     SELECT COALESCE(SUM(oi.totalPrice), 0)
     FROM OrderItem oi
     WHERE oi.vendor.id = :vendorId
     AND oi.order.orderStatus='DELIVERED'
      """)
    BigDecimal getVendorRevenue(@Param("vendorId")Long vendorId);


    @Query("""
     SELECT COUNT(DISTINCT oi.order.user.userName)
     FROM OrderItem oi
     WHERE oi.vendor.id = :vendorId
     AND oi.order.orderStatus='DELIVERED'
      """)
    long countCustomers(@Param("vendorId")Long vendorId);


    @Query("""
       SELECT COUNT(oi) > 0
       FROM OrderItem oi
       WHERE oi.order.user = :user
       AND oi.variant.product = :product
       AND oi.order.orderStatus = 'DELIVERED'
       """)
    boolean hasPurchasedProduct(
            @Param("user") User user,
            @Param("product") Product product
    );

    @Query("""
    SELECT COALESCE(SUM(oi.quantity),0)
    FROM OrderItem oi
    WHERE oi.vendor.id=:vendorId
    AND oi.order.orderStatus='DELIVERED'
    """)
    Long getTotalProductsSold(
            @Param("vendorId") Long vendorId);

    @Query("""
SELECT COALESCE(SUM(oi.totalPrice),0)
FROM OrderItem oi
WHERE oi.variant.product.id = :productId
""")
    BigDecimal getProductRevenue(
            @Param("productId") Long productId);

    @Query("""
SELECT COALESCE(SUM(oi.totalPrice),0)
FROM OrderItem oi
WHERE oi.variant.product.category.id = :categoryId
""")
    BigDecimal getCategoryRevenue(
            @Param("categoryId") Long categoryId);
}
