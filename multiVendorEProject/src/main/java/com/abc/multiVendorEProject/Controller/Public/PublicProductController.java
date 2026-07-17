package com.abc.multiVendorEProject.Controller.Public;

import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductDetailsResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductListResponseDTO;
import com.abc.multiVendorEProject.service.Public.PublicProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class PublicProductController {

    private final PublicProductService customerProductService;

    @GetMapping
    public Page<ProductListResponseDTO> getAllProducts(
            Pageable pageable) {

        return customerProductService.getAllProducts(pageable);
    }

    @GetMapping("/{productId}")
    public ProductDetailsResponseDto getProductById(
            @PathVariable Long productId) {

        return customerProductService.getProductById(productId);
    }

    @GetMapping("/category/{categoryId}")
    public Page<ProductListResponseDTO> getProductsByCategory(
            @PathVariable Long categoryId,
            Pageable pageable) {

        return customerProductService.getProductsByCategory(
                categoryId,
                pageable
        );
    }

    @GetMapping("/brand/{brandId}")
    public Page<ProductListResponseDTO> getProductsByBrand(
            @PathVariable Long brandId,
            Pageable pageable) {

        return customerProductService.getProductsByBrand(
                brandId,
                pageable
        );
    }

    @GetMapping("/vendor/{vendorId}")
    public Page<ProductDetailsResponseDto> getProductsByVendor(
            @PathVariable Long vendorId,
            Pageable pageable) {

        return customerProductService.getProductsByVendor(
                vendorId,
                pageable
        );
    }

    @GetMapping("/search")
    public Page<ProductListResponseDTO> searchProducts(
            @RequestParam(required = false) String keyword,
            Pageable pageable) {

        return customerProductService.searchProducts(
                keyword,
                pageable
        );
    }

    @GetMapping("/latest")
    public Page<ProductListResponseDTO> getLatestProducts(
            Pageable pageable) {

        return customerProductService.getLatestProducts(pageable);
    }

    @GetMapping("/trending")
    public Page<ProductListResponseDTO> getTrendingProducts(
            Pageable pageable) {

        return customerProductService.getTrendingProducts(pageable);
    }

    @GetMapping("/popular")
    public Page<ProductListResponseDTO> getMostPopularProducts(
            Pageable pageable) {

        return customerProductService.getMostPopularProducts(pageable);
    }

}