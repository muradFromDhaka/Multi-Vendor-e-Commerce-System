package com.abc.SpringSecurityExample.repository;

import com.abc.SpringSecurityExample.DTOs.projectDtos.ProductResponseDto;
import com.abc.SpringSecurityExample.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    @EntityGraph(attributePaths = {"category","brand","vendor"})
    Page<Product> findByDeletedFalse(Pageable pageable);

    @EntityGraph(attributePaths = {"category","brand","vendor"})
    @Query("""
    SELECT p
    FROM Product p
    WHERE p.deleted = false
    """)
    Page<Product> findAllProducts(Pageable pageable);


    @EntityGraph(attributePaths = {"category","brand","vendor"})
    Page<Product> findByVendorId(Long vendorId, Pageable pageable);

    List<Product> findByVendorIdAndDeletedFalse(Long vendorId);


    @Query("""
        SELECT p FROM Product p
        WHERE p.category.id = :categoryId
        AND p.deleted = false
        ORDER BY p.soldCount DESC
    """)
    List<Product> findPopularByCategory(Long categoryId, Pageable pageable);

    @EntityGraph(attributePaths = {"brand", "vendor", "category"})
    @Query("""
    SELECT p FROM Product p
    WHERE p.brand.id = :brandId
    AND p.deleted = false
    ORDER BY p.soldCount DESC
""")
    List<Product> findPopularByBrand(@Param("brandId") Long brandId, Pageable pageable);

    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

    @Query("""
        SELECT p FROM Product p
        WHERE p.discountPrice IS NOT NULL
        AND p.discountPrice < p.price
        AND p.deleted = false
    """)
    Page<Product> findDiscountedProducts(Pageable pageable);

    @Query("""
        SELECT p FROM Product p
        WHERE p.deleted = false
        ORDER BY p.createdAt DESC
    """)
    Page<Product> findLatestProducts(Pageable pageable);

    @Query("""
        SELECT p FROM Product p
        WHERE p.deleted = false
        ORDER BY p.soldCount DESC
    """)
    Page<Product> findMostPopularProducts(Pageable pageable);

    @Query("""
        SELECT p FROM Product p
        WHERE p.deleted = false
        AND p.soldCount > 0
        ORDER BY p.soldCount DESC
    """)
    Page<Product> findTrendingProducts(Pageable pageable);


    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    @Query("""
        SELECT DISTINCT p
        FROM Product p
        LEFT JOIN p.category c
        LEFT JOIN p.vendor v
        WHERE 
            LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(v.shopName) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    Page<Product> searchProducts(@Param("keyword") String keyword, Pageable pageable);


    Page<Product> findByBrandId(Long brandId, Pageable pageable);


}
