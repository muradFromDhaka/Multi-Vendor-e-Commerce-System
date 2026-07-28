package com.abc.multiVendorEProject.repository;

import com.abc.multiVendorEProject.entity.Product;
import com.abc.multiVendorEProject.entity.Review;
import com.abc.multiVendorEProject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // =========================
    // Basic CRUD Queries
    // =========================

    Page<Review> findByProduct(Product product, Pageable pageable);

    List<Review> findByUser(User user);

    Optional<Review> findByUserAndProduct(User user, Product product);

    Long countByProduct(Product product);

    Long countByProductAndRating(Product product, Double rating);

    // =========================
    // Product Review Summary
    // =========================

    @Query("""
        SELECT COALESCE(AVG(r.rating), 0)
        FROM Review r
        WHERE r.product = :product
        """)
    Double getAverageRatingByProduct(@Param("product") Product product);

    // =========================
    // Global Review Statistics (Admin Dashboard)
    // =========================

    @Query("""
        SELECT COALESCE(AVG(r.rating), 0)
        FROM Review r
        """)
    BigDecimal getAverageRating();

    List<Review> findTop6ByOrderByCreatedAtDesc();
}