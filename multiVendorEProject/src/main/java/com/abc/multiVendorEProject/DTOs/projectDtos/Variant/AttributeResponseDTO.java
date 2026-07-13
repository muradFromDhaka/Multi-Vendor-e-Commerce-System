package com.abc.multiVendorEProject.DTOs.projectDtos.Variant;


public record AttributeResponseDTO(
         Long id,
         String name,
         Long categoryId,
         String categoryName
) {}