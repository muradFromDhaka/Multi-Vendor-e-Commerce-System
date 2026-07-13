package com.abc.multiVendorEProject.Controller;

import com.abc.multiVendorEProject.DTOs.projectDtos.CategoryRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.CategoryResponseDto;
import com.abc.multiVendorEProject.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public Page<CategoryResponseDto> getAllCategories(Pageable pageable) {
        return categoryService.getAllCategories(pageable);
    }


    // ---------------- CREATE CATEGORY ----------------
    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(
            @RequestPart("category") CategoryRequestDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        CategoryResponseDto created = categoryService.createCategory(dto, image);
        return ResponseEntity.ok(created);
    }

    // ---------------- UPDATE CATEGORY ----------------
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(
            @PathVariable Long id,
            @RequestPart("category") CategoryRequestDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        CategoryResponseDto updated = categoryService.updateCategory(id, dto, image);
        return ResponseEntity.ok(updated);
    }

    // ---------------- DELETE CATEGORY ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) throws IOException {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    // ---------------- GET CATEGORY BY ID ----------------
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    // ---------------- GET ALL ROOT CATEGORIES ----------------
    @GetMapping("/root")
    public ResponseEntity<Page<CategoryResponseDto>> getAllRootCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return ResponseEntity.ok(categoryService.getAllRootCategories(page, size, sortBy,sortDir));
    }

    // ---------------- GET SUBCATEGORIES ----------------
    @GetMapping("/{id}/subcategories")
    public ResponseEntity<Page<CategoryResponseDto>> getSubCategories(
                                 @PathVariable Long id,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String sortBy,
                                 @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return ResponseEntity.ok(categoryService.getSubCategories(id, page, size, sortBy, sortDir));
    }
}
