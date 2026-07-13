package com.abc.multiVendorEProject.mapper;

import com.abc.multiVendorEProject.DTOs.projectDtos.CategoryRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.CategoryResponseDto;
import com.abc.multiVendorEProject.entity.Category;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

public class CategoryMapper {

    private CategoryMapper() {}

    public static Category toEntity(CategoryRequestDto dto, Category parent) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setImageUrl(dto.getImageUrl());
        category.setParent(parent);
        return category;
    }

    public static CategoryResponseDto toResponseDto(Category category) {
        if (category == null) return null;

        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setImageUrl(category.getImageUrl());
        dto.setParentId(
                category.getParent() != null ? category.getParent().getId() : null
        );
        dto.setParentName(
                category.getParent() != null ? category.getParent().getName() : null
        );

        if (category.getSubCategories() != null) {
            dto.setSubCategories(
                    category.getSubCategories()
                            .stream()
                            .map(CategoryMapper::toResponseDto)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public static Page<CategoryResponseDto> toResponseDtoList(Page<Category> categories) {
        return categories.map(CategoryMapper::toResponseDto);
    }
}

