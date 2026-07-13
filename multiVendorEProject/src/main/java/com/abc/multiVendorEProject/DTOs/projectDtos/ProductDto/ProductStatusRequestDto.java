package com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto;

import com.abc.multiVendorEProject.enums.ProductStatus;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ProductStatusRequestDto {
    private ProductStatus status;
}
