package com.abc.multiVendorEProject.Controller;

import com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto.OrderResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.OrderItemResponseDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorResponseDto;
import com.abc.multiVendorEProject.service.VendorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    // Vendor Registration
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VendorResponseDto> createVendor(

            @RequestPart("vendor")
            VendorRequestDto dto,

            @RequestPart(value = "logo", required = false)
            MultipartFile logo,

            @RequestPart(value = "banner", required = false)
            MultipartFile banner) {

        return ResponseEntity.ok(
                vendorService.createVendor(dto, logo, banner)
        );
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

    // Logged-in Vendor Profile
    @GetMapping("/me")
    public ResponseEntity<VendorResponseDto> getMyVendor() {

        return ResponseEntity.ok(vendorService.getMyVendor());
    }

    // Logged-in Vendor Orders
    @GetMapping("/me/orders")
    public ResponseEntity<Page<OrderResponseDto>> myOrders(Pageable pageable) {

        return ResponseEntity.ok(vendorService.getMyOrders(pageable));
    }

    // Logged-in Vendor Order Items
    @GetMapping("/me/order-items")
    public ResponseEntity<List<OrderItemResponseDTO>> myOrderItems() {

        return ResponseEntity.ok(vendorService.getMyOrderItems());
    }
}