package com.abc.multiVendorEProject.DTOs.projectDtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequestDTO {

    @NotNull
    private Long variantId;

    @Min(1)
    private Integer quantity;



}
