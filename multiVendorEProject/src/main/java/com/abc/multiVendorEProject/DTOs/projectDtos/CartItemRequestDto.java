package com.abc.multiVendorEProject.DTOs.projectDtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemRequestDto {

    @NotNull
    private Long productVariantId;

    @NotNull
    @Min(1)
    private Integer quantity;
}
