package com.abc.multiVendorEProject.repository.VariantRepository;

import com.abc.multiVendorEProject.entity.Product;
import com.abc.multiVendorEProject.entity.Variant.ProductVariant;
import com.abc.multiVendorEProject.entity.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductVariantRepository
        extends JpaRepository<ProductVariant, Long> {


    long count();

    long countByProductVendor(Vendor vendor);

    long countByProductVendorAndStockLessThan(Vendor vendor, Integer stock);

    long countByProductVendorAndStock(Vendor vendor, Integer stock);

    long countByStock(Integer stock);

    long countByStockLessThan(Integer stock);


    boolean existsBySku(String sku);

    boolean existsBySkuAndIdNot(String sku, Long id);

    Optional<ProductVariant> findBySku(String sku);

    Optional<ProductVariant> findById(Long variantId);

    List<ProductVariant> findByProductId(Long productId);

    Page<ProductVariant> findByProductId(
            Long productId,
            Pageable pageable
    );

    long countByStockBetween(Integer startStock, Integer endStock);

    long countByProductId(Long productId);


    /*
     * ==========================
     * Cheapest Variant
     * ==========================
     */

    Optional<ProductVariant> findFirstByProductIdOrderByPriceAsc(
            Long productId
    );


    /*
     * ==========================
     * Discount Variants
     * ==========================
     */

    List<ProductVariant> findByProductIdAndDiscountPriceIsNotNull(
            Long productId
    );


    /*
     * ==========================
     * Discount Products
     * ==========================
     */

    @Query("""
        SELECT DISTINCT pv.product
        FROM ProductVariant pv
        WHERE pv.discountPrice IS NOT NULL
        AND pv.discountPrice < pv.price
        AND pv.product.deleted = false
    """)
    Page<Product> findDiscountedProducts(Pageable pageable);


    /*
     * ==========================
     * Price Filter
     * ==========================
     */

    @Query("""
        SELECT DISTINCT pv.product
        FROM ProductVariant pv
        WHERE pv.price BETWEEN :min AND :max
        AND pv.product.deleted = false
    """)
    Page<Product> findProductsByPrice(
            BigDecimal min,
            BigDecimal max,
            Pageable pageable
    );


    @Query("""
SELECT COALESCE(SUM(pv.stock),0)
FROM ProductVariant pv
""")
    Long getTotalStockQuantity();


    Page<ProductVariant> findByProductVendorId(Long vendorId, Pageable pageable);


    @Query("""
SELECT pv
FROM ProductVariant pv
WHERE pv.product.vendor.id = :vendorId
AND pv.stock > 0
AND pv.stock <= :threshold
""")
    Page<ProductVariant> findLowStockVariants(
            @Param("vendorId") Long vendorId,
            @Param("threshold") Integer threshold,
            Pageable pageable
    );


    @Query("""
SELECT pv
FROM ProductVariant pv
WHERE pv.product.vendor.id = :vendorId
AND pv.stock = 0
""")
    Page<ProductVariant> findOutOfStockVariants(
            @Param("vendorId") Long vendorId,
            Pageable pageable
    );


    @Query("""
    SELECT pv
    FROM ProductVariant pv
    WHERE pv.product.vendor.id = :vendorId
      AND pv.stock > 0
      AND pv.stock <= :maxStock
    ORDER BY pv.stock ASC
""")
    List<ProductVariant> findTop5LowStockProducts(
            @Param("vendorId") Long vendorId,
            @Param("maxStock") Integer maxStock,
            Pageable pageable
    );

}