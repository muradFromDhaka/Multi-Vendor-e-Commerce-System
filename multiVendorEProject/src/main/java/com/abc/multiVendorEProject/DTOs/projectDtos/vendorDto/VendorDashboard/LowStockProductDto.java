package com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorDashboard;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LowStockProductDto {

    private Long variantId;

    private String productName;

    private String sku;

    private Integer stock;

}
