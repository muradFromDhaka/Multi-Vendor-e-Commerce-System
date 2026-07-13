package com.abc.multiVendorEProject.mapper.Variant;

import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.ProductVariantRequestDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.ProductVariantResponseDTO;
import com.abc.multiVendorEProject.entity.Variant.ProductVariant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductVariantMapper {


    public static ProductVariantResponseDTO toResponse(ProductVariant variant) {

        if (variant == null) {
            return null;
        }

        ProductVariantResponseDTO dto = new ProductVariantResponseDTO();

        dto.setId(variant.getId());
        dto.setSku(variant.getSku());
        dto.setPrice(variant.getPrice());
        dto.setDiscountPrice(variant.getDiscountPrice());
        dto.setStock(variant.getStock());
        dto.setImageUrls(variant.getImageUrls());

        if (variant.getProduct() != null) {
            dto.setProductId(variant.getProduct().getId());
            dto.setProductName(variant.getProduct().getName());
        }

        if (variant.getAttributeValues() != null) {
            dto.setAttributeValues(
                    variant.getAttributeValues()
                            .stream()
                            .map(AttributeValueMapper::toResponse)
                            .toList()
            );
        }

        return dto;
    }

    public static ProductVariant toEntity(ProductVariantRequestDTO dto) {

        if (dto == null) {
            return null;
        }

        ProductVariant variant = new ProductVariant();

        variant.setSku(dto.getSku());
        variant.setPrice(dto.getPrice());
        variant.setDiscountPrice(dto.getDiscountPrice());
        variant.setStock(dto.getStock());

        return variant;
    }
}