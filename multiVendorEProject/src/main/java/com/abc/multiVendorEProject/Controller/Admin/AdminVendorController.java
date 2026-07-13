package com.abc.multiVendorEProject.Controller.Admin;

import com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto.OrderResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductDetailsResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorStatsDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorSummaryDto;
import com.abc.multiVendorEProject.enums.VendorStatus;
import com.abc.multiVendorEProject.service.Customer.CustomerProductService;
import com.abc.multiVendorEProject.service.VendorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/vendors")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminVendorController {

    private final VendorService vendorService;
    private final CustomerProductService productService;

    // Get All Vendors
    @GetMapping
    public ResponseEntity<List<VendorResponseDto>> getVendors(
            @RequestParam(required = false) VendorStatus status,
            @RequestParam(required = false) String search) {

        return ResponseEntity.ok(
                vendorService.getVendors(status, search)
        );
    }

    // Get Vendor Details
    @GetMapping("/{id}")
    public ResponseEntity<VendorResponseDto> getVendorById(
            @PathVariable Long id) {

        return ResponseEntity.ok(vendorService.getVendorById(id));
    }

    // Update Vendor
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public VendorResponseDto updateVendor(
            @PathVariable Long id,
            @RequestPart("vendor") String vendorJson,
            @RequestPart(value = "logo", required = false) MultipartFile logo,
            @RequestPart(value = "banner", required = false) MultipartFile banner
    ) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        VendorRequestDto dto = mapper.readValue(
                vendorJson,
                VendorRequestDto.class
        );

        return vendorService.updateVendor(id, dto, logo, banner);
    }

    // Update Vendor Status
    @PutMapping("/{id}/status")
    public ResponseEntity<VendorResponseDto> updateStatus(
            @PathVariable Long id,
            @RequestParam VendorStatus status) {

        return ResponseEntity.ok(
                vendorService.updateVendorStatus(id, status)
        );
    }

    @GetMapping("/summary")
    public ResponseEntity<VendorSummaryDto> vendorSummary() {

        return ResponseEntity.ok(
                vendorService.getVendorSummary()
        );

    }

    @GetMapping("/{vendorId}/products")
    public ResponseEntity<Page<ProductDetailsResponseDto>> getVendorProducts(
            @PathVariable Long vendorId,
            Pageable pageable) {

        return ResponseEntity.ok(
                productService.getProductsByVendor(vendorId,pageable)
        );
    }

    @GetMapping("/{vendorId}/orders")
    public ResponseEntity<Page<OrderResponseDto>> getVendorOrders(
            @PathVariable Long vendorId,
            Pageable pageable) {

        return ResponseEntity.ok(
                vendorService.getVendorOrders(vendorId,pageable)
        );
    }


    @GetMapping("/{vendorId}/stats")
    public ResponseEntity<VendorStatsDto> getVendorStats(
            @PathVariable Long vendorId) {

        return ResponseEntity.ok(
                vendorService.getVendorStats(vendorId)
        );
    }


    // Delete Vendor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendor(
            @PathVariable Long id) {

        vendorService.deleteVendor(id);

        return ResponseEntity.noContent().build();
    }
}