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
    private final VendorOrderService vendorOrderService;

    @GetMapping
    public Page<InventoryListResponseDto> getInventory(
            Pageable pageable
    ) {

        Vendor loggedInVendor = vendorOrderService.getLoggedInVendor();

        return vendorInventoryService.getInventory(
                loggedInVendor.getId(),
                pageable
        );
    }

    @GetMapping("/low-stock")
    public Page<InventoryListResponseDto> getLowStockInventory(
            Pageable pageable
    ) {

        Vendor loggedInVendor = vendorOrderService.getLoggedInVendor();

        return vendorInventoryService.getLowStockInventory(
                loggedInVendor.getId(),
                pageable
        );
    }

    @GetMapping("/out-of-stock")
    public Page<InventoryListResponseDto> getOutOfStockInventory(
            Pageable pageable
    ) {

        Vendor loggedInVendor = vendorOrderService.getLoggedInVendor();

        return vendorInventoryService.getOutOfStockInventory(
                loggedInVendor.getId(),
                pageable
        );
    }

    @PutMapping("/{variantId}")
    public InventoryListResponseDto updateStock(
            @PathVariable Long variantId,
            @Valid @RequestBody UpdateInventoryRequestDto request
    ) {

        Vendor loggedInVendor = vendorOrderService.getLoggedInVendor();

        return vendorInventoryService.updateStock(
                loggedInVendor.getId(),
                variantId,
                request
        );
    }


    @GetMapping("/{variantId}")
    public InventoryListResponseDto getInventoryDetails(
            @PathVariable Long variantId
    ) {

        Vendor loggedInVendor =
                vendorOrderService.getLoggedInVendor();

        return vendorInventoryService.getInventoryDetails(
                loggedInVendor.getId(),
                variantId
        );
    }
}
