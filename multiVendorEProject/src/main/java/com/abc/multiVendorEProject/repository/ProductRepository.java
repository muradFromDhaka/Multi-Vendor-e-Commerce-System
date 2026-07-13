package com.abc.multiVendorEProject.repository;

import com.abc.multiVendorEProject.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    long count();

    Optional<Product> findByIdAndDeletedFalse(Long id);

    @EntityGraph(attributePaths = {"variants"})
    Page<Product> findByDeletedFalse(Pageable pageable);

    @EntityGraph(attributePaths = {"variants"})
    Page<Product> findByVendorId(Long vendorId, Pageable pageable);

    @EntityGraph(attributePaths = {"variants"})
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    @EntityGraph(attributePaths = {"variants"})
    Page<Product> findByBrandId(Long brandId, Pageable pageable);

    @EntityGraph(attributePaths = {"variants"})
    @Query("""
        SELECT DISTINCT p
        FROM Product p
        LEFT JOIN p.category c
        LEFT JOIN p.vendor v
        WHERE p.deleted = false
        AND (
            LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(v.shopName) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
    """)
    Page<Product> searchProducts(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {"variants"})
    @Query("""
        SELECT p
        FROM Product p
        WHERE p.deleted = false
        ORDER BY p.createdAt DESC
    """)
    Page<Product> findLatestProducts(Pageable pageable);

    @EntityGraph(attributePaths = {"variants"})
    @Query("""
        SELECT p
        FROM Product p
        WHERE p.deleted = false
        ORDER BY p.soldCount DESC
    """)
    Page<Product> findMostPopularProducts(Pageable pageable);

    @EntityGraph(attributePaths = {"variants"})
    @Query("""
        SELECT p
        FROM Product p
        WHERE p.deleted = false
        AND p.soldCount > 0
        ORDER BY p.soldCount DESC
    """)
    Page<Product> findTrendingProducts(Pageable pageable);

    long countByVendorId(Long vendorId);

    Optional<Product> findByIdAndVendorIdAndDeletedFalse(Long productId, Long vendorId);

    Page<Product> findByVendorIdAndDeletedFalse(Long vendorId, Pageable pageable);
}