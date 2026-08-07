package com.abc.multiVendorEProject.Controller.Vendor;

import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorDashboard.VendorDashboardResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorDashboard.VendorPerformanceResponseDto;
import com.abc.multiVendorEProject.service.Vendor.VendorDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/vendor/dashboard")
@RequiredArgsConstructor
public class VendorDashboardController {

    private final VendorDashboardService vendorDashboardService;

    @GetMapping
    public ResponseEntity<VendorDashboardResponseDto> getDashboard() {

        return ResponseEntity.ok(
                vendorDashboardService.getDashboard()
        );
    }


    @GetMapping("/performance")
    public ResponseEntity<VendorPerformanceResponseDto> getPerformance(
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate) {

        return ResponseEntity.ok(
                vendorDashboardService.getPerformance(
                        fromDate,
                        toDate
                )
        );
    }
}
