package com.abc.multiVendorEProject.Controller.Vendor;

import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorDashboard.VendorDashboardResponseDto;
import com.abc.multiVendorEProject.service.Vendor.VendorDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vendor/dashboard")
@RequiredArgsConstructor
public class VendorDashboardController {

    private final VendorDashboardService dashboardService;

    @GetMapping
    public ResponseEntity<VendorDashboardResponseDto> getDashboard() {

        return ResponseEntity.ok(
                dashboardService.getDashboard()
        );
    }
}
