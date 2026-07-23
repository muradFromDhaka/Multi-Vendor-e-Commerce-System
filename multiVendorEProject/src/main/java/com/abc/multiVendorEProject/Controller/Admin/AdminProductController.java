package com.abc.multiVendorEProject.Controller.Admin;

import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductDetailsResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductListResponseDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductStatusRequestDto;
import com.abc.multiVendorEProject.service.Admin.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;

    // =====================================================
    // Create Product
    // =====================================================

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductDetailsResponseDto createProduct(
            @RequestPart("product") ProductRequestDto dto,
            @RequestPart(value = "images", required = false) MultipartFile[] images) {

        return adminProductService.createProduct(dto, images);
    }

    // =====================================================
    // Update Product
    // =====================================================

    @PutMapping(value = "/{productId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductDetailsResponseDto updateProduct(
            @PathVariable Long productId,
            @RequestPart("product") ProductRequestDto dto,
            @RequestPart(value = "images", required = false) MultipartFile[] images) {

        return adminProductService.updateProduct(productId, dto, images);
    }

    // =====================================================
    // Get All Products
    // =====================================================

    @GetMapping
    public Page<ProductListResponseDTO> getAllProducts(
            @PageableDefault(
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.DESC
            )Pageable pageable) {

        return adminProductService.getAllProducts(pageable);
    }

    // =====================================================
    // Get Product By Id
    // =====================================================

    @GetMapping("/{productId}")
    public ProductDetailsResponseDto getProductById(
            @PathVariable Long productId) {

        return adminProductService.getProductById(productId);
    }

    // =====================================================
    // Delete Product (Soft Delete)
    // =====================================================

    @DeleteMapping("/{productId}")
    public String deleteProduct(@PathVariable Long productId) {

        adminProductService.deleteProduct(productId);

        return "Product deleted successfully.";
    }

    // =====================================================
    // Change Product Status
    // =====================================================

    @PatchMapping("/{productId}/status")
    public ProductDetailsResponseDto changeProductStatus(
            @PathVariable Long productId,
            @RequestBody ProductStatusRequestDto dto) {

        return adminProductService.changeProductStatus(
                productId,
                dto.getStatus());
    }


    @GetMapping("/search")
    public ResponseEntity<Page<ProductListResponseDTO>> searchProducts(

            @RequestParam String keyword,

            @PageableDefault(size = 10)
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                adminProductService.searchProducts(
                        keyword,
                        pageable
                )
        );
    }


}