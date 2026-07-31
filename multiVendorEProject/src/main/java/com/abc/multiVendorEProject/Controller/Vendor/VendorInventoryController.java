package com.abc.multiVendorEProject.Controller.Vendor;

import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.Inventory.InventoryListResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.Inventory.UpdateInventoryRequestDto;
import com.abc.multiVendorEProject.entity.Vendor;
import com.abc.multiVendorEProject.service.Vendor.VendorInventoryService;
import com.abc.multiVendorEProject.service.Vendor.VendorOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor/inventory")
@RequiredArgsConstructor
public class VendorInventoryController {

    private final VendorInventoryService vendorInventoryService;

    @GetMapping
    public Page<InventoryListResponseDto> getInventory(
            Pageable pageable
    ) {


        return vendorInventoryService.getInventory(
                pageable
        );
    }

    @GetMapping("/low-stock")
    public Page<InventoryListResponseDto> getLowStockInventory(
            Pageable pageable
    ) {


        return vendorInventoryService.getLowStockInventory(
                pageable
        );
    }

    @GetMapping("/out-of-stock")
    public Page<InventoryListResponseDto> getOutOfStockInventory(
            Pageable pageable
    ) {


        return vendorInventoryService.getOutOfStockInventory(
                pageable
        );
    }

    @PutMapping("/{variantId}")
    public InventoryListResponseDto updateStock(
            @PathVariable Long variantId,
            @Valid @RequestBody UpdateInventoryRequestDto request
    ) {


        return vendorInventoryService.updateStock(
                variantId,
                request
        );
    }


    @GetMapping("/{variantId}")
    public InventoryListResponseDto getInventoryDetails(
            @PathVariable Long variantId
    ) {


        return vendorInventoryService.getInventoryDetails(
                variantId
        );
    }
}
