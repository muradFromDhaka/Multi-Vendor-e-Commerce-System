package com.abc.multiVendorEProject.DTOs.projectDtos.Variant;


public record AttributeRequestDTO(
        String name, // Color, Size
        Long categoryId
    ) {}
