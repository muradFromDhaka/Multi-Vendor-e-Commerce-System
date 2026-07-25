package com.abc.multiVendorEProject.service.Vendor;

import com.abc.multiVendorEProject.Config.ResourceNotFoundException;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.Inventory.InventoryListResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.Inventory.UpdateInventoryRequestDto;
import com.abc.multiVendorEProject.entity.Variant.ProductVariant;
import com.abc.multiVendorEProject.enums.StockStatus;
import com.abc.multiVendorEProject.repository.VariantRepository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendorInventoryService {

    private final ProductVariantRepository productVariantRepository;

    private static final int LOW_STOCK_THRESHOLD = 5;


    public Page<InventoryListResponseDto> getInventory(
            Long vendorId,
            Pageable pageable
    ) {

        return productVariantRepository
                .findByProductVendorId(vendorId, pageable)
                .map(this::mapToInventoryDto);
    }

    public Page<InventoryListResponseDto> getLowStockInventory(
            Long vendorId,
            Pageable pageable
    ) {

        return productVariantRepository
                .findLowStockVariants(
                        vendorId,
                        LOW_STOCK_THRESHOLD,
                        pageable
                )
                .map(this::mapToInventoryDto);
    }
    public Page<InventoryListResponseDto> getOutOfStockInventory(
            Long vendorId,
            Pageable pageable
    ) {

        return productVariantRepository
                .findOutOfStockVariants(vendorId, pageable)
                .map(this::mapToInventoryDto);
    }


    public InventoryListResponseDto getInventoryDetails(
            Long vendorId,
            Long variantId
    ) {

        ProductVariant variant = productVariantRepository
                .findById(variantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Variant not found."));

        // Verify Vendor Ownership
        if (!variant.getProduct()
                .getVendor()
                .getId()
                .equals(vendorId)) {

            throw new AccessDeniedException(
                    "You are not authorized to view this inventory.");
        }

        return mapToInventoryDto(variant);
    }




    public InventoryListResponseDto updateStock(
            Long vendorId,
            Long variantId,
            UpdateInventoryRequestDto request
    ) {

        ProductVariant variant = productVariantRepository
                .findById(variantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Variant not found."));

        // Verify Vendor Ownership
        if (!variant.getProduct().getVendor().getId().equals(vendorId)) {
            throw new AccessDeniedException(
                    "You are not authorized to update this inventory.");
        }

        if (request.getStock() == null) {
            throw new IllegalArgumentException("Stock is required.");
        }

        // Update Stock
        variant.setStock(request.getStock());

        ProductVariant updatedVariant =
                productVariantRepository.save(variant);

        return mapToInventoryDto(updatedVariant);
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
