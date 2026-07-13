package com.abc.multiVendorEProject.DTOs.projectDtos.Variant;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Setter @Getter
public class ProductVariantRequestDTO {

    @NotBlank
    private String sku;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal price;

    @DecimalMin("0.00")
    private BigDecimal discountPrice;

    @NotNull
    @Min(0)
    private Integer stock;

    @NotNull
    private Long productId;

    private List<String> imageUrls;

    @NotEmpty
    private List<Long> attributeValueIds;
}
