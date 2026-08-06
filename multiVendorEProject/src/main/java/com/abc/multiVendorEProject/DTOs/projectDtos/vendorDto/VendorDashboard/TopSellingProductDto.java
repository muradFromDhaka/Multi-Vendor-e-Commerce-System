package com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorDashboard;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopSellingProductDto {

    private Long productId;

    private String productName;

    private String imageUrl;

    private Long soldQuantity;

    private BigDecimal revenue;

}
