package com.abc.multiVendorEProject.Controller.Vendor;


import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.UpdateVendorOrderStatusRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorOrderDetailsResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorOrderListResponseDto;
import com.abc.multiVendorEProject.service.Vendor.VendorOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/vendor/orders")
@RequiredArgsConstructor
public class VendorOrderController {

    private final VendorOrderService vendorOrderService;

    // =====================================================
    // Vendor Orders
    // =====================================================

    @GetMapping
    public Page<VendorOrderListResponseDto> getVendorOrders(
            Pageable pageable) {

        return vendorOrderService.getVendorOrders(pageable);
    }

    @GetMapping("/{vendorOrderId}")
    public VendorOrderDetailsResponseDto getVendorOrderDetails(
            @PathVariable Long vendorOrderId) {

        return vendorOrderService.getVendorOrderDetails(vendorOrderId);
    }

    @PatchMapping("/{vendorOrderId}/status")
    public VendorOrderDetailsResponseDto updateVendorOrderStatus(
            @PathVariable Long vendorOrderId,
            @Valid @RequestBody UpdateVendorOrderStatusRequestDto request) {

        return vendorOrderService.updateVendorOrderStatus(
                vendorOrderId,
                request);
    }

    // =====================================================
    // Vendor Dashboard
    // =====================================================

    @GetMapping("/dashboard/revenue")
    public BigDecimal getVendorRevenue() {

        return vendorOrderService.getVendorRevenue();
    }

    @GetMapping("/dashboard/customers")
    public long getVendorCustomers() {

        return vendorOrderService.getVendorCustomers();
    }

    @GetMapping("/dashboard/products-sold")
    public Long getVendorProductsSold() {

        return vendorOrderService.getVendorProductsSold();
    }

}