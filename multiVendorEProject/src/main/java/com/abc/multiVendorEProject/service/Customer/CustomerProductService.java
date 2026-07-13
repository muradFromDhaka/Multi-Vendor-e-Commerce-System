package com.abc.multiVendorEProject.service.Customer;

import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductDetailsResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductListResponseDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.ProductVariantResponseDTO;
import com.abc.multiVendorEProject.entity.Brand;
import com.abc.multiVendorEProject.entity.Category;
import com.abc.multiVendorEProject.entity.Product;
import com.abc.multiVendorEProject.entity.Variant.ProductVariant;
import com.abc.multiVendorEProject.mapper.ProductMapper;
import com.abc.multiVendorEProject.mapper.Variant.ProductVariantMapper;
import com.abc.multiVendorEProject.repository.BrandRepository;
import com.abc.multiVendorEProject.repository.CategoryRepository;
import com.abc.multiVendorEProject.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;


//===============================Helper Method=========================

    private Product getProductOrThrow(Long productId) {

        return productRepository.findByIdAndDeletedFalse(productId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Product not found with id : " + productId));
    }

    private Category getCategoryOrThrow(Long categoryId) {

        return categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Category not found with id : " + categoryId));
    }

    private Brand getBrandOrThrow(Long brandId) {

        return brandRepository.findById(brandId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Brand not found with id : " + brandId));
    }

    private ProductListResponseDTO mapToProductListDto(Product product) {

        ProductVariant variant = product.getVariants()
                .stream()
                .findFirst()
                .orElse(null);

        BigDecimal price = BigDecimal.ZERO;
        BigDecimal discountPrice = null;

        if (variant != null) {
            price = variant.getPrice();
            discountPrice = variant.getDiscountPrice();
        }

        String thumbnail = null;
        if (product.getImageUrls() != null && !product.getImageUrls().isEmpty()) {
            thumbnail = product.getImageUrls().get(0);
        }

        return ProductMapper.toListDto(
                product,
                price,
                discountPrice,
                thumbnail,
                product.getVariants().size()
        );
    }


    private Page<ProductListResponseDTO> mapToProductList(Page<Product> products) {

        return products.map(this::mapToProductListDto);
    }




//    ================================Original Methods===================================
//                  ======================================================

    @Transactional
    public Page<ProductListResponseDTO> getAllProducts(Pageable pageable) {

        return mapToProductList(
                productRepository.findByDeletedFalse(pageable)
        );
    }


    @Transactional
    public ProductDetailsResponseDto getProductById(Long productId) {

        Product product = getProductOrThrow(productId);

        return ProductMapper.toDetailsDto(
                product,
                product.getVariants()
                        .stream()
                        .map(ProductVariantMapper::toResponse)
                        .toList(),
                product.getVariants().size()
        );
    }


    @Transactional
    public Page<ProductListResponseDTO> getLatestProducts(Pageable pageable) {

        return mapToProductList(
                productRepository.findLatestProducts(pageable)
        );
    }

    @Transactional
    public Page<ProductListResponseDTO> getTrendingProducts(Pageable pageable) {

        return mapToProductList(
                productRepository.findTrendingProducts(pageable)
        );
    }

    @Transactional
    public Page<ProductListResponseDTO> getMostPopularProducts(Pageable pageable) {

        return mapToProductList(
                productRepository.findMostPopularProducts(pageable)
        );
    }


    @Transactional
    public Page<ProductListResponseDTO> getProductsByCategory(
            Long categoryId,
            Pageable pageable) {

        // Optional Validation
        getCategoryOrThrow(categoryId);

        return mapToProductList(
                productRepository.findByCategoryId(categoryId, pageable)
        );
    }


    @Transactional
    public Page<ProductListResponseDTO> getProductsByBrand(
            Long brandId,
            Pageable pageable) {

        // Optional Validation
        getBrandOrThrow(brandId);

        return mapToProductList(
                productRepository.findByBrandId(brandId, pageable)
        );
    }


    @Transactional
    public Page<ProductDetailsResponseDto> getProductsByVendor(
            Long vendorId,
            Pageable pageable) {

        return productRepository
                .findByVendorIdAndDeletedFalse(vendorId, pageable)
                .map(product -> {

                    List<ProductVariantResponseDTO> variants =
                            product.getVariants()
                                    .stream()
                                    .map(ProductVariantMapper::toResponse)
                                    .toList();

                    return ProductMapper.toDetailsDto(
                            product,
                            variants,
                            variants.size()
                    );
                });
    }




    @Transactional
    public Page<ProductListResponseDTO> searchProducts(
            String keyword,
            Pageable pageable) {

        if (keyword == null || keyword.isBlank()) {
            return getAllProducts(pageable);
        }

        return mapToProductList(
                productRepository.searchProducts(
                        keyword.trim(),
                        pageable
                )
        );
    }

}
