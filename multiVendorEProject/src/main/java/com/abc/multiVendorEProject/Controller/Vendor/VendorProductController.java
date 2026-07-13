package com.abc.multiVendorEProject.Controller.Vendor;

import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductDetailsResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductRequestDto;
import com.abc.multiVendorEProject.service.Vendor.VendorProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/vendor/products")
@RequiredArgsConstructor
public class VendorProductController {

    private final VendorProductService vendorProductService;

    // =====================================================
    // Create Product
    // =====================================================

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductDetailsResponseDto createProduct(
            @RequestPart("product") ProductRequestDto dto,
            @RequestPart(value = "images", required = false)
            MultipartFile[] images) {

        return vendorProductService.createProduct(dto, images);
    }

    // =====================================================
    // Update Product
    // =====================================================

    @PutMapping(value = "/{productId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductDetailsResponseDto updateProduct(
            @PathVariable Long productId,
            @RequestPart("product") ProductRequestDto dto,
            @RequestPart(value = "images", required = false)
            MultipartFile[] images) {

        return vendorProductService.updateProduct(
                productId,
                dto,
                images
        );
    }

    // =====================================================
    // Get My Products
    // =====================================================

    @GetMapping
    public Page<ProductDetailsResponseDto> getMyProducts(
            Pageable pageable) {

        return vendorProductService.getMyProducts(pageable);
    }

    // =====================================================
    // Get Product Details
    // =====================================================

    @GetMapping("/{productId}")
    public ProductDetailsResponseDto getProductById(
            @PathVariable Long productId) {

        return vendorProductService.getProductById(productId);
    }

    // =====================================================
    // Delete Product
    // =====================================================

    @DeleteMapping("/{productId}")
    public String deleteProduct(
            @PathVariable Long productId) {

        vendorProductService.deleteProduct(productId);

        return "Product deleted successfully.";
    }

}