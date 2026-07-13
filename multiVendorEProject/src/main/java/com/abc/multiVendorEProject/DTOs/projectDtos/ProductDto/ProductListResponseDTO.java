package com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto;

import com.abc.multiVendorEProject.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductListResponseDTO {

    private Long id;

    private String name;

    private String thumbnailUrl;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private Double averageRating;

    private Integer totalReviews;

    private Long variantId;

    private Long categoryId;

    private String categoryName;

    private Integer totalVariants;

    private ProductStatus status;
}
