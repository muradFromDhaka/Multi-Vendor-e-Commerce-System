package com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.Inventory;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInventoryRequestDto {

    @Min(0)
    private Integer stock;
}
