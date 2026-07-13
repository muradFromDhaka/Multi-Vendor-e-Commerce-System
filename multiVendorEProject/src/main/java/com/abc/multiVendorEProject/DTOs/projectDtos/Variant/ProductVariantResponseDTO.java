package com.abc.multiVendorEProject.DTOs.projectDtos.Variant;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Setter @Getter
public class ProductVariantResponseDTO {

    private Long id;
    private String sku;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Integer stock;

    private Long productId;
    private String productName;

    private List<String> imageUrls;

    private List<AttributeValueResponseDTO> attributeValues;
}