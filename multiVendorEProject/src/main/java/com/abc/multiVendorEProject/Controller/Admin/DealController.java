package com.abc.multiVendorEProject.Controller.Admin;

import com.abc.multiVendorEProject.DTOs.projectDtos.DealRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.DealResponseDto;
import com.abc.multiVendorEProject.service.DealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/deals")
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;

    // ==========================================
    // Create Deal
    // ==========================================
    @PostMapping
    public ResponseEntity<DealResponseDto> createDeal(
            @Valid @RequestBody DealRequestDto dto) {

        return ResponseEntity.ok(
                dealService.createDeal(dto)
        );
    }

    // ==========================================
    // Update Deal
    // ==========================================
    @PutMapping("/{id}")
    public ResponseEntity<DealResponseDto> updateDeal(
            @PathVariable Long id,
            @Valid @RequestBody DealRequestDto dto) {

        return ResponseEntity.ok(
                dealService.updateDeal(id, dto)
        );
    }

    // ==========================================
    // Delete Deal
    // ==========================================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeal(
            @PathVariable Long id) {

        dealService.deleteDeal(id);

        return ResponseEntity.ok(
                "Deal deleted successfully."
        );
    }

    // ==========================================
    // Get Deal By Id
    // ==========================================
    @GetMapping("/{id}")
    public ResponseEntity<DealResponseDto> getDealById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                dealService.getDealById(id)
        );
    }

    // ==========================================
    // Admin - Get All Deals
    // ==========================================
    @GetMapping
    public ResponseEntity<Page<DealResponseDto>> getAllDeals(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "createdAt")
            String sortBy,

            @RequestParam(defaultValue = "desc")
            String sortDir) {

        return ResponseEntity.ok(
                dealService.getAllDeals(
                        page,
                        size,
                        sortBy,
                        sortDir
                )
        );
    }

    // ==========================================
    // Public - Active Deals (Flash Sale)
    // ==========================================
    @GetMapping("/active")
    public ResponseEntity<Page<DealResponseDto>> getActiveDeals(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "createdAt")
            String sortBy,

            @RequestParam(defaultValue = "desc")
            String sortDir) {

        return ResponseEntity.ok(
                dealService.getActiveDeals(
                        page,
                        size,
                        sortBy,
                        sortDir
                )
        );
    }

    // ==========================================
    // Public - Active Deal By Product
    // ==========================================
    @GetMapping("/product/{productId}")
    public ResponseEntity<DealResponseDto> getActiveDealByProduct(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                dealService.getActiveDealByProduct(productId)
        );
    }

}