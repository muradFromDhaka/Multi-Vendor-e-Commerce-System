package com.abc.multiVendorEProject.Controller.Admin;

import com.abc.multiVendorEProject.DTOs.projectDtos.Customer.CustomerStatisticsDto;
import com.abc.multiVendorEProject.DTOs.securityDtos.UserResponseDto;
import com.abc.multiVendorEProject.service.Admin.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/customers")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final AdminUserService adminUserService;

    // ========================= Customer List =========================

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getAllCustomers(
            @PageableDefault(sort = "userName") Pageable pageable) {

        return ResponseEntity.ok(
                adminUserService.getAllCustomers(pageable)
        );
    }

    // ========================= Search Customer =========================

    @GetMapping("/search")
    public ResponseEntity<Page<UserResponseDto>> searchCustomers(
            @RequestParam String searchTerm,
            Pageable pageable) {

        return ResponseEntity.ok(
                adminUserService.searchCustomers(searchTerm, pageable)
        );
    }

    // ========================= Active Customers =========================

    @GetMapping("/active")
    public ResponseEntity<Page<UserResponseDto>> getActiveCustomers(
            Pageable pageable) {

        return ResponseEntity.ok(
                adminUserService.getActiveCustomers(pageable)
        );
    }

    // ========================= Disabled Customers =========================

    @GetMapping("/disabled")
    public ResponseEntity<Page<UserResponseDto>> getDisabledCustomers(
            Pageable pageable) {

        return ResponseEntity.ok(
                adminUserService.getDisabledCustomers(pageable)
        );
    }

    // ========================= Enable Customer =========================

    @PatchMapping("/{username}/enable")
    public ResponseEntity<Void> enableCustomer(
            @PathVariable String username) {

        adminUserService.enableCustomer(username);

        return ResponseEntity.noContent().build();
    }

    // ========================= Disable Customer =========================

    @PatchMapping("/{username}/disable")
    public ResponseEntity<Void> disableCustomer(
            @PathVariable String username) {

        adminUserService.disableCustomer(username);

        return ResponseEntity.noContent().build();
    }

    // ========================= Delete Customer =========================

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable String username) {

        adminUserService.deleteCustomer(username);

        return ResponseEntity.noContent().build();
    }

    // ========================= Statistics =========================

    @GetMapping("/statistics")
    public ResponseEntity<CustomerStatisticsDto> getCustomerStatistics() {

        return ResponseEntity.ok(
                adminUserService.getCustomerStatistics()
        );
    }

    // ========================= customer Details =========================

    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDto> getCustomer(
            @PathVariable String username) {

        return ResponseEntity.ok(
                adminUserService.getCustomer(username)
        );
    }

}