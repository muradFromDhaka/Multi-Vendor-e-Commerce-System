package com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorDashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorPerformanceResponseDto {

    private BigDecimal revenue;

    private Long orders;

    private Long productsSold;

    private Long newCustomers;

    private BigDecimal averageOrderValue;
}
