package com.abc.multiVendorEProject.repository;


import com.abc.multiVendorEProject.entity.Order;
import com.abc.multiVendorEProject.entity.Payment;
import com.abc.multiVendorEProject.enums.PaymentMethod;
import com.abc.multiVendorEProject.enums.PaymentProvider;
import com.abc.multiVendorEProject.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

//    ======================Lookup======================

    Optional<Payment> findByTransactionId(String transactionId);

    Optional<Payment> findByOrder(Order order);

    boolean existsByTransactionId(String transactionId);

    Optional<Payment> findByOrderId(Long orderId);

    Page<Payment> findByPaymentMethod(
            PaymentMethod paymentMethod,
            Pageable pageable);

    boolean existsByOrder(Order order);
    long countByPaymentMethod(PaymentMethod paymentMethod);

//    =============================Listing & Search=====================

    Page<Payment> findAllByOrderByCreatedAtDesc(
            Pageable pageable);

    Page<Payment> findByPaymentStatusOrderByPaidAtDesc(
            PaymentStatus paymentStatus,
            Pageable pageable);


    Page<Payment> findByPaymentStatusAndPaymentMethod(
            PaymentStatus paymentStatus,
            PaymentMethod paymentMethod,
            Pageable pageable);

    Page<Payment> findByOrderUserUserNameContainingIgnoreCase(
            String username,
            Pageable pageable);

    Optional<Payment> findByOrderOrderNumber(String orderNumber);

    Optional<Payment> findByRefundTransactionId(
            String refundTransactionId);


//    =========================================================

    long countByPaymentStatus(PaymentStatus status);

    @Query("""
select coalesce(sum(p.amount),0)
from Payment p
where p.paymentStatus='PAID'
""")
    BigDecimal getGrossRevenue();

    @Query("""
select coalesce(sum(p.amount),0)
from Payment p
where p.paymentStatus='PAID'
and p.paidAt between :start and :end
""")
    BigDecimal getGrossRevenueBetween(
            LocalDateTime start,
            LocalDateTime end);


    @Query("""
select coalesce(avg(p.amount),0)
from Payment p
where p.paymentStatus = :paymentStatus
""")
    BigDecimal getAveragePaymentAmount(
            @Param("paymentStatus")
            PaymentStatus paymentStatus);


//    ==============================================================

    @Query("""
select coalesce(sum(p.refundedAmount),0)
from Payment p
where p.paymentStatus='REFUNDED'
""")
    BigDecimal getTotalRefundAmount();

    @Query("""
select coalesce(sum(p.refundedAmount),0)
from Payment p
where p.paymentStatus='REFUNDED'
and p.refundedAt between :start and :end
""")
    BigDecimal getRefundAmountBetween(
            LocalDateTime start,
            LocalDateTime end);


    @Query("""
select coalesce(avg(p.refundedAmount),0)
from Payment p
where p.paymentStatus = :paymentStatus
""")
    BigDecimal getAverageRefundAmount(
            @Param("paymentStatus")
            PaymentStatus paymentStatus);


    @Query("""
select coalesce(max(p.refundedAmount),0)
from Payment p
where p.paymentStatus = :paymentStatus
""")
    BigDecimal getHighestRefundAmount(
            @Param("paymentStatus")
            PaymentStatus paymentStatus);


    @Query("""
select coalesce(min(p.refundedAmount),0)
from Payment p
where p.paymentStatus = :paymentStatus
""")
    BigDecimal getLowestRefundAmount(
            @Param("paymentStatus")
            PaymentStatus paymentStatus);



    @Query("""
select coalesce(max(p.amount),0)
from Payment p
where p.paymentStatus = :paymentStatus
""")
    BigDecimal getHighestPaymentAmount(
            @Param("paymentStatus")
            PaymentStatus paymentStatus);


    @Query("""
select coalesce(min(p.amount),0)
from Payment p
where p.paymentStatus = :paymentStatus
""")
    BigDecimal getLowestPaymentAmount(
            @Param("paymentStatus")
            PaymentStatus paymentStatus);


    @Query("""
select coalesce(sum(p.amount),0)
from Payment p
where p.paymentStatus = 'PAID'
and p.paymentMethod = :method
""")
    BigDecimal getRevenueByPaymentMethod(
            @Param("method")
            PaymentMethod method);


    @Query("""
select coalesce(avg(p.amount),0)
from Payment p
where p.paymentStatus = 'PAID'
and p.paymentMethod = :method
""")
    BigDecimal getAveragePaymentByMethod(
            @Param("method")
            PaymentMethod method);


    @Query("""
select count(p)
from Payment p
where p.paymentStatus='REFUNDED'
and p.refundedAt between :start and :end
""")
    long countRefundBetween(
            LocalDateTime start,
            LocalDateTime end);

//=============================Finance Dashboard==========================

    @Query("""
select coalesce(sum(p.amount),0)
from Payment p
where p.paymentStatus='PENDING'
""")
    BigDecimal getPendingPaymentAmount();

    @Query("""
select coalesce(sum(p.amount),0)
from Payment p
where p.paymentStatus='FAILED'
""")
    BigDecimal getFailedPaymentAmount();


    @Query("""
select coalesce(sum(p.amount),0)
from Payment p
where p.paymentStatus='CANCELLED'
""")
    BigDecimal getCancelledPaymentAmount();


//    =======================Payment Provider=====================

    @Query("""
select coalesce(sum(p.amount),0)
from Payment p
where p.paymentStatus = 'PAID'
and p.paymentProvider = :provider
""")
    BigDecimal getRevenueByPaymentProvider(
            @Param("provider")
            PaymentProvider provider);

    long countByPaymentProvider(PaymentProvider provider);

    @Query("""
select coalesce(avg(p.amount),0)
from Payment p
where p.paymentStatus = 'PAID'
and p.paymentProvider = :provider
""")
    BigDecimal getAveragePaymentByProvider(
            @Param("provider")
            PaymentProvider provider);


}
