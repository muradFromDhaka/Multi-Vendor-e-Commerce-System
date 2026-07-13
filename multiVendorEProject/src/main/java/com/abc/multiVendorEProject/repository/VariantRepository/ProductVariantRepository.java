package com.abc.multiVendorEProject.repository.VariantRepository;

import com.abc.multiVendorEProject.entity.Product;
import com.abc.multiVendorEProject.entity.Variant.ProductVariant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductVariantRepository
        extends JpaRepository<ProductVariant, Long> {


    long count();

    long countByStock(Integer stock);

    long countByStockLessThan(Integer stock);


    boolean existsBySku(String sku);

    boolean existsBySkuAndIdNot(String sku, Long id);

    Optional<ProductVariant> findBySku(String sku);

    Optional<ProductVariant> findById(Long id);

    List<ProductVariant> findByProductId(Long productId);

    Page<ProductVariant> findByProductId(
            Long productId,
            Pageable pageable
    );

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

}