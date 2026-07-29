package com.abc.multiVendorEProject.Controller.Public;

import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductDetailsResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductListResponseDTO;
import com.abc.multiVendorEProject.service.Public.PublicProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class PublicProductController {

    private final PublicProductService publicProductService;

    @GetMapping
    public Page<ProductListResponseDTO> getAllProducts(
            Pageable pageable) {

        return publicProductService.getAllProducts(pageable);
    }

    @GetMapping("/{productId}")
    public ProductDetailsResponseDto getProductById(
            @PathVariable Long productId) {

        return publicProductService.getProductById(productId);
    }

    @GetMapping("/category/{categoryId}")
    public Page<ProductListResponseDTO> getProductsByCategory(
            @PathVariable Long categoryId,
            Pageable pageable) {

        return publicProductService.getProductsByCategory(
                categoryId,
                pageable
        );
    }

    @GetMapping("/brand/{brandId}")
    public Page<ProductListResponseDTO> getProductsByBrand(
            @PathVariable Long brandId,
            Pageable pageable) {

        return publicProductService.getProductsByBrand(
                brandId,
                pageable
        );
    }

    @GetMapping("/vendor/{vendorId}")
    public Page<ProductListResponseDTO> getProductsByVendor(
            @PathVariable Long vendorId,
            Pageable pageable) {

        return publicProductService.getProductsByVendor(
                vendorId,
                pageable
        );
    }

    @GetMapping("/search")
    public Page<ProductListResponseDTO> searchProducts(
            @RequestParam(required = false) String keyword,
            Pageable pageable) {

        return publicProductService.searchProducts(
                keyword,
                pageable
        );
    }

    @GetMapping("/latest")
    public Page<ProductListResponseDTO> getLatestProducts(
            Pageable pageable) {

        return publicProductService.getLatestProducts(pageable);
    }

    @GetMapping("/trending")
    public Page<ProductListResponseDTO> getTrendingProducts(
            Pageable pageable) {

        return publicProductService.getTrendingProducts(pageable);
    }

    @GetMapping("/popular")
    public Page<ProductListResponseDTO> getMostPopularProducts(
            Pageable pageable) {

        return publicProductService.getMostPopularProducts(pageable);
    }

    @GetMapping("/top-rated")
    public ResponseEntity<Page<ProductListResponseDTO>> topRated(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "8")
            int size
    ) {

        return ResponseEntity.ok(
                publicProductService.getTopRated(
                        page,
                        size,
                        "averageRating",
                        "desc"
                )
        );
    }

    @GetMapping("/deals")
    public Page<ProductListResponseDTO> getDealsProducts(
            Pageable pageable) {

        return publicProductService
                .getDealsProducts(pageable);

    }

}