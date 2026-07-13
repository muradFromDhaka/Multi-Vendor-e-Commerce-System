package com.abc.multiVendorEProject.entity.Variant;

import com.abc.multiVendorEProject.entity.BaseEntity;
import com.abc.multiVendorEProject.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "product_variants")
@Getter @Setter
public class ProductVariant extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String sku;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(precision = 10, scale = 2)
    private BigDecimal discountPrice;

    @Column(nullable = false)
    private Integer stock = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToMany
    @JoinTable(
            name = "variant_attribute_values",
            joinColumns = @JoinColumn(name = "variant_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_value_id")
    )
    private Set<AttributeValue> attributeValues;


    @ElementCollection
    @CollectionTable(
            name = "product_variant_images",
            joinColumns = @JoinColumn(name = "variant_id")
    )
    @Column(name = "image_url")
    private List<String> imageUrls;

}