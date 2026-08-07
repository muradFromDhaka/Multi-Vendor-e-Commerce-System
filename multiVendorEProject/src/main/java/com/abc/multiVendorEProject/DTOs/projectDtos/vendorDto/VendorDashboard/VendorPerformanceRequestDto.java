package com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorDashboard;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorPerformanceRequestDto {

    @NotNull
    private LocalDate fromDate;

    @NotNull
    private LocalDate toDate;
}