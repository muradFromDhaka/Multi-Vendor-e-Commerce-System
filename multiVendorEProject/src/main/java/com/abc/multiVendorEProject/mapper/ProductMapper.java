package com.abc.multiVendorEProject.mapper;

import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductDetailsResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductListResponseDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.ProductVariantResponseDTO;
import com.abc.multiVendorEProject.entity.Product;
import com.abc.multiVendorEProject.entity.Variant.ProductVariant;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ProductMapper {

    public static Product toEntity(ProductRequestDto dto) {

        if (dto == null) {
            return null;
        }

        Product product = new Product();

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setStatus(dto.getStatus());
        product.setReleaseDate(dto.getReleaseDate());
        product.setImageUrls(dto.getImageUrls());

        return product;
    }


    public static ProductDetailsResponseDto toDetailsDto(
            Product product,
            List<ProductVariantResponseDTO> variants,
            Integer totalVariants) {

        if (product == null) {
            return null;
        }

        return ProductDetailsResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())

                .status(product.getStatus())
                .releaseDate(product.getReleaseDate())

                .averageRating(product.getAverageRating())
                .totalReviews(product.getTotalReviews())

                .imageUrls(product.getImageUrls())

                .productVariants(variants)
                .totalVariants(totalVariants)

                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())

                .brandId(product.getBrand().getId())
                .brandName(product.getBrand().getName())

                .vendorId(product.getVendor().getId())
                .vendorName(product.getVendor().getShopName())

                .soldCount(product.getSoldCount())

                .build();
    }


    public static ProductListResponseDTO toListDto(Product product) {

        if (product == null) {
            return null;
        }

        ProductListResponseDTO dto = new ProductListResponseDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());

        dto.setAverageRating(product.getAverageRating());
        dto.setTotalReviews(product.getTotalReviews());

        dto.setCategoryId(product.getCategory().getId());
        dto.setCategoryName(product.getCategory().getName());

        dto.setBrandId(product.getBrand().getId());
        dto.setBrandName(product.getBrand().getName());

        dto.setStatus(product.getStatus());

        Set<ProductVariant> variants = product.getVariants();

        if (variants == null) {
            variants = Collections.emptySet();
        }

        dto.setTotalVariants(variants.size());

        ProductVariant firstVariant = variants.stream()
                .findFirst()
                .orElse(null);

        // Product Image
        if (product.getImageUrls() != null && !product.getImageUrls().isEmpty()) {
            dto.setThumbnailUrl(product.getImageUrls().get(0));
        }

        if (firstVariant != null) {

            dto.setProductVariantId(firstVariant.getId());
            dto.setPrice(firstVariant.getPrice());
            dto.setDiscountPrice(firstVariant.getDiscountPrice());

            // যদি Product Image না থাকে, তখন Variant Image ব্যবহার করো
            if (dto.getThumbnailUrl() == null
                    && firstVariant.getImageUrls() != null
                    && !firstVariant.getImageUrls().isEmpty()) {

                dto.setThumbnailUrl(firstVariant.getImageUrls().get(0));
            }
        }

        System.out.println("==========================");
        System.out.println("Product ID: " + product.getId());
        System.out.println("Images: " + product.getImageUrls());
        System.out.println("Variants: " + product.getVariants().size());
        System.out.println("==========================");

        return dto;
    }
}