package com.abc.multiVendorEProject.service.Vendor;

import com.abc.multiVendorEProject.Config.ResourceNotFoundException;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.Inventory.InventoryListResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.Inventory.UpdateInventoryRequestDto;
import com.abc.multiVendorEProject.entity.Variant.ProductVariant;
import com.abc.multiVendorEProject.entity.Vendor;
import com.abc.multiVendorEProject.enums.StockStatus;
import com.abc.multiVendorEProject.repository.VariantRepository.ProductVariantRepository;
import com.abc.multiVendorEProject.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendorInventoryService {

    private final ProductVariantRepository productVariantRepository;
    private final VendorRepository vendorRepository;

    private static final int LOW_STOCK_THRESHOLD = 5;




    private Vendor getLoggedInVendor() {

        String userName = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return vendorRepository.findByUserUserName(userName)
                .orElseThrow(() ->
                        new RuntimeException("Vendor not found."));
    }

    public Page<InventoryListResponseDto> getInventory(
            Pageable pageable
    ) {

        Vendor vendor = getLoggedInVendor();
        return productVariantRepository
                .findByProductVendorId(vendor.getId(), pageable)
                .map(this::mapToInventoryDto);
    }

    public Page<InventoryListResponseDto> getLowStockInventory(
            Pageable pageable
    ) {

        Vendor vendor = getLoggedInVendor();

        return productVariantRepository
                .findLowStockVariants(
                        vendor.getId(),
                        LOW_STOCK_THRESHOLD,
                        pageable
                )
                .map(this::mapToInventoryDto);
    }
    public Page<InventoryListResponseDto> getOutOfStockInventory(
            Pageable pageable
    ) {

        Vendor vendor = getLoggedInVendor();

        return productVariantRepository
                .findOutOfStockVariants(vendor.getId(), pageable)
                .map(this::mapToInventoryDto);
    }


    public InventoryListResponseDto getInventoryDetails(
            Long variantId
    ) {

        Vendor vendor = getLoggedInVendor();

        ProductVariant variant = productVariantRepository
                .findById(variantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Variant not found."));

        // Verify Vendor Ownership
        if (!variant.getProduct()
                .getVendor()
                .getId()
                .equals(vendor.getId())) {

            throw new AccessDeniedException(
                    "You are not authorized to view this inventory.");
        }

        return mapToInventoryDto(variant);
    }




    public InventoryListResponseDto updateStock(
            Long variantId,
            UpdateInventoryRequestDto request
    ) {

        Vendor vendor = getLoggedInVendor();

        ProductVariant variant = productVariantRepository
                .findById(variantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Variant not found."));

        // Verify Vendor Ownership
        if (!variant.getProduct().getVendor().getId().equals(vendor.getId())) {
            throw new AccessDeniedException(
                    "You are not authorized to update this inventory.");
        }

        if (request.getStock() == null) {
            throw new IllegalArgumentException("Stock is required.");
        }

        // Update Stock
        if (request.getStock() < 0) {
            throw new IllegalArgumentException(
                    "Stock cannot be negative.");
        }

        variant.setStock(request.getStock());

        try {

            ProductVariant updatedVariant =
                    productVariantRepository.save(variant);

            return mapToInventoryDto(updatedVariant);

        } catch (ObjectOptimisticLockingFailureException ex) {

            throw new RuntimeException(
                    "Stock was updated by another request. Please refresh and try again.");
        }

    }


//        =========================Helper Method =========================

    private StockStatus getStockStatus(Integer stock) {

        if (stock == null || stock == 0) {
            return StockStatus.OUT_OF_STOCK;
        }

        if (stock <= LOW_STOCK_THRESHOLD) {
            return StockStatus.LOW_STOCK;
        }

        return StockStatus.IN_STOCK;
    }

    private InventoryListResponseDto mapToInventoryDto(ProductVariant variant) {

        InventoryListResponseDto dto = new InventoryListResponseDto();

        dto.setVariantId(variant.getId());

        dto.setProductName(variant.getProduct().getName());

        dto.setSku(variant.getSku());

        dto.setPrice(variant.getPrice());

        dto.setDiscountPrice(variant.getDiscountPrice());

        dto.setStock(variant.getStock());

        dto.setUpdatedAt(variant.getUpdatedAt());

        // Product Image
        if (variant.getImageUrls() != null && !variant.getImageUrls().isEmpty()) {
            dto.setImageUrl(variant.getImageUrls().get(0));
        } else if (variant.getProduct().getImageUrls() != null
                && !variant.getProduct().getImageUrls().isEmpty()) {

            dto.setImageUrl(
                    variant.getProduct().getImageUrls().get(0)
            );
        }

        // Attributes
        String attributes = variant.getAttributeValues()
                .stream()
                .map(attributeValue ->
                        attributeValue.getAttribute().getName()
                                + ": "
                                + attributeValue.getValue())
                .collect(Collectors.joining(", "));

        dto.setAttributes(attributes);

        dto.setStockStatus(getStockStatus(variant.getStock()));

        return dto;
    }
}
