package com.abc.multiVendorEProject.mapper;

import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductDetailsResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductListResponseDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.ProductVariantResponseDTO;
import com.abc.multiVendorEProject.entity.Product;

import java.math.BigDecimal;
import java.util.List;

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

                .variants(variants)
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

    public static ProductListResponseDTO toListDto(
            Product product,
            BigDecimal price,
            BigDecimal discountPrice,
            String thumbnail,
            Integer totalVariants) {

        if (product == null) {
            return null;
        }

        ProductListResponseDTO dto = new ProductListResponseDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());

        dto.setThumbnailUrl(thumbnail);

        dto.setPrice(price);
        dto.setDiscountPrice(discountPrice);

        dto.setAverageRating(product.getAverageRating());
        dto.setTotalReviews(product.getTotalReviews());

        product.getVariants()
                .stream()
                .findFirst()
                .ifPresent(v -> dto.setVariantId(v.getId()));

        dto.setTotalVariants(totalVariants);

        dto.setCategoryId(product.getCategory().getId());
        dto.setCategoryName(product.getCategory().getName());

        dto.setStatus(product.getStatus());

        return dto;
    }
}