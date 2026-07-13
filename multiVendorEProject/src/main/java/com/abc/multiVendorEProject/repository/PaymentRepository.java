package com.abc.multiVendorEProject.repository;


import com.abc.multiVendorEProject.entity.Order;
import com.abc.multiVendorEProject.entity.Payment;
import com.abc.multiVendorEProject.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByTransactionId(String transactionId);

    Optional<Payment> findByOrder(Order order);

    boolean existsByTransactionId(String transactionId);

    Page<Payment> findByStatus(
            PaymentStatus status,
            Pageable pageable
    );
}
