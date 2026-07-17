package com.abc.multiVendorEProject.DTOs.projectDtos.AdminDashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerStatisticsDto {

    private long totalCustomers;
    private long activeCustomers;
    private long disabledCustomers;

}

