package com.abc.multiVendorEProject.repository;

import com.abc.multiVendorEProject.entity.Deal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DealRepository extends JpaRepository<Deal, Long> {

    // =========================================
    // Admin - All Deals
    // =========================================
    @Override
    @EntityGraph(attributePaths = "product")
    Page<Deal> findAll(Pageable pageable);

    // =========================================
    // Admin - Deal Details
    // =========================================
    @Override
    @EntityGraph(attributePaths = "product")
    Optional<Deal> findById(Long id);

    // =========================================
    // Home Page - Active Deals
    // =========================================
    @EntityGraph(attributePaths = "product")
    @Query("""
            SELECT d
            FROM Deal d
            WHERE d.active = true
            AND d.startTime <= CURRENT_TIMESTAMP
            AND d.endTime >= CURRENT_TIMESTAMP
            ORDER BY d.createdAt DESC
            """)
    Page<Deal> findActiveDeals(Pageable pageable);

    // =========================================
    // Product Details Page
    // =========================================
    @EntityGraph(attributePaths = "product")
    @Query("""
            SELECT d
            FROM Deal d
            WHERE d.product.id = :productId
            AND d.active = true
            AND d.startTime <= CURRENT_TIMESTAMP
            AND d.endTime >= CURRENT_TIMESTAMP
            """)
    Optional<Deal> findActiveDealByProductId(Long productId);

    // =========================================
    // Admin - Active Deals
    // =========================================
    @EntityGraph(attributePaths = "product")
    Page<Deal> findByActiveTrue(Pageable pageable);

    // =========================================
    // Admin - Inactive Deals
    // =========================================
    @EntityGraph(attributePaths = "product")
    Page<Deal> findByActiveFalse(Pageable pageable);

}