package com.abc.multiVendorEProject.Controller.Admin;

import com.abc.multiVendorEProject.DTOs.projectDtos.AdminDashboard.AdminDashboardResponseDto;
import com.abc.multiVendorEProject.service.Admin.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    // =====================================================
    // Dashboard
    // =====================================================

    @GetMapping
    public AdminDashboardResponseDto getDashboard() {

        return adminDashboardService.getDashboard();

    }

}