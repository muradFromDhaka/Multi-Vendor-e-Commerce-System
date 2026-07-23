package com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class TopVendorDto {

    private String topVendorName;
    private BigDecimal topVendorRevenue;
}
