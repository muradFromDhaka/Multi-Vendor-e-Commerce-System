package com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TopVendorDto {

    private String topVendorName;
    private BigDecimal topVendorRevenue;
}
