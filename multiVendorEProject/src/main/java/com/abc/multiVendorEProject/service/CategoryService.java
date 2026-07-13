package com.abc.multiVendorEProject.service;

import com.abc.multiVendorEProject.DTOs.projectDtos.CategoryRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.CategoryResponseDto;
import com.abc.multiVendorEProject.entity.Category;
import com.abc.multiVendorEProject.mapper.CategoryMapper;
import com.abc.multiVendorEProject.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final FileStorageService fileStorageService;

    // ---------------- CREATE CATEGORY ----------------
    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto dto, MultipartFile image) throws IOException {

        Category parent = null;
        if (dto.getParentId() != null) {
            parent = categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
        }

        Category category = CategoryMapper.toEntity(dto, parent);

        // Handle image upload
        if (image != null && !image.isEmpty()) {
            String fileName = fileStorageService.saveFile(image);
            category.setImageUrl(fileName);
        }

        Category saved = categoryRepository.save(category);
        return CategoryMapper.toResponseDto(saved);
    }

    // ---------------- UPDATE CATEGORY ----------------
    @Transactional
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto dto, MultipartFile image) throws IOException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(dto.getName());
        category.setParent(
                dto.getParentId() != null
                        ? categoryRepository.findById(dto.getParentId())
                        .orElseThrow(() -> new RuntimeException("Parent category not found"))
                        : null
        );

        // Handle image update
        if (image != null && !image.isEmpty()) {
            if (category.getImageUrl() != null) {
                fileStorageService.deleteFile(category.getImageUrl());
            }
            String fileName = fileStorageService.saveFile(image);
            category.setImageUrl(fileName);
        }

        Category updated = categoryRepository.save(category);
        return CategoryMapper.toResponseDto(updated);
    }

    // ---------------- DELETE CATEGORY ----------------
    @Transactional
    public void deleteCategory(Long id) throws IOException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Delete image if exists
        if (category.getImageUrl() != null) {
            fileStorageService.deleteFile(category.getImageUrl());
        }

        categoryRepository.delete(category);
    }

    // ---------------- GET CATEGORY BY ID ----------------
    @Transactional(readOnly = true)
    public CategoryResponseDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return CategoryMapper.toResponseDto(category);
    }

    // ---------------- GET ALL ROOT CATEGORIES ----------------
    @Transactional(readOnly = true)
    public Page<CategoryResponseDto> getAllRootCategories(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Category> rootCategories = categoryRepository.findByParentIsNull(pageable);
        return CategoryMapper.toResponseDtoList(rootCategories);
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponseDto> getSubCategories(Long parentId, int page, int size,String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Category> subCategories = categoryRepository.findByParentId(parentId, pageable);

        return CategoryMapper.toResponseDtoList(subCategories);
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponseDto> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(CategoryMapper::toResponseDto);
    }

}
