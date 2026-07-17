package com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto;


import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.ProductVariantResponseDTO;
import com.abc.multiVendorEProject.enums.ProductStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailsResponseDto {

    private Long id;
    private String name;
    private String description;
    private ProductStatus status;
    private LocalDate releaseDate;
    private Double averageRating;
    private Integer totalReviews;
    private List<String> imageUrls;
    private Integer totalVariants;

    private List<ProductVariantResponseDTO> productVariants;

    // Flattened relationships
    private Long categoryId;
    private String categoryName;

    private Long brandId;
    private String brandName;

    private Long vendorId;
    private String vendorName;

    // Backend managed field
    private Integer soldCount;
}
