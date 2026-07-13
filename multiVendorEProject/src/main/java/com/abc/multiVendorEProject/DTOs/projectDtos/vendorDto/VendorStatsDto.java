package com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class VendorStatsDto {

    private long totalProducts;
    private long totalOrders;
    private BigDecimal totalRevenue;
    private long totalCustomers;

}
