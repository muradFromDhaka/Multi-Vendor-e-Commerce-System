package com.abc.multiVendorEProject.mapper;

import com.abc.multiVendorEProject.DTOs.projectDtos.BrandRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.BrandResponseDto;
import com.abc.multiVendorEProject.entity.Brand;

public class BrandMapper {

    private BrandMapper() {
        // prevent instantiation
    }

    // -------- RequestDto → Entity (Create) --------
    public static Brand toEntity(BrandRequestDto dto) {
        if (dto == null) return null;

        Brand brand = new Brand();
        brand.setName(dto.getName());
        brand.setDescription(dto.getDescription());
        brand.setLogoUrl(dto.getLogoUrl());
        return brand;
    }

    // -------- Entity → ResponseDto --------
    public static BrandResponseDto toResponseDto(Brand brand) {
        if (brand == null) return null;

        BrandResponseDto dto = new BrandResponseDto();
        dto.setId(brand.getId());
        dto.setName(brand.getName());
        dto.setDescription(brand.getDescription());
        dto.setLogoUrl(brand.getLogoUrl());
        return dto;
    }

}

