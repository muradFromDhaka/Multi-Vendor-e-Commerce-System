package com.abc.multiVendorEProject.repository;


import com.abc.multiVendorEProject.entity.Order;
import com.abc.multiVendorEProject.entity.User;
import com.abc.multiVendorEProject.enums.OrderStatus;
import com.abc.multiVendorEProject.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // 1️⃣ All orders by user
    Page<Order> findByUser(User user, Pageable pageable);

    // 2️⃣ Orders by status
    Page<Order> findByOrderStatus(OrderStatus status, Pageable pageable);

    // 3️⃣ Orders by user + status
    Page<Order> findByUserAndOrderStatus(User user, OrderStatus status,Pageable pageable);

    // 4️⃣ Orders by multiple statuses
    Page<Order> findByOrderStatusIn(List<OrderStatus> statuses,Pageable pageable);

    Optional<Order> findByOrderNumber(String orderNumber);

    boolean existsByOrderNumber(String orderNumber);

    long countByPaymentStatus(PaymentStatus paymentStatus);

    Long countByOrderStatus(OrderStatus orderStatus);

    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    Page<Order> findAllByOrderByCreatedAtDesc(Pageable pageable);


    @Query("""
    SELECT COALESCE(SUM(o.totalPrice), 0)
    FROM Order o
    WHERE o.orderStatus = 'DELIVERED'
    AND o.createdAt BETWEEN :startDate AND :endDate
    """)
    BigDecimal getRevenueBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("""
    SELECT COALESCE(SUM(o.totalPrice), 0)
    FROM Order o
    WHERE o.orderStatus = 'DELIVERED'
    """)
    BigDecimal getTotalRevenue();

    List<Order> findByPaymentStatus(PaymentStatus paymentStatus);


//    =====================================================

    @Query("""
    SELECT COALESCE(AVG(o.totalPrice), 0)
    FROM Order o
    WHERE o.paymentStatus = :paymentStatus
""")
    BigDecimal getAverageOrderValue(
            @Param("paymentStatus") PaymentStatus paymentStatus
    );

    @Query("""
    SELECT COALESCE(MAX(o.totalPrice), 0)
    FROM Order o
    WHERE o.paymentStatus = :paymentStatus
""")
    BigDecimal getHighestOrderValue(
            @Param("paymentStatus") PaymentStatus paymentStatus
    );



}
