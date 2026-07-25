package com.abc.multiVendorEProject.mapper;

import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.WishlistDto.WishlistProductDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.WishlistDto.WishlistResponseDto;
import com.abc.multiVendorEProject.entity.Product;
import com.abc.multiVendorEProject.entity.Variant.ProductVariant;
import com.abc.multiVendorEProject.entity.Wishlist;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class WishlistMapper {

    public WishlistResponseDto toDto(Wishlist wishlist ) {

        if (wishlist == null) {
            return null;
        }

        WishlistResponseDto dto = new WishlistResponseDto();

        dto.setWishlistId(wishlist.getId());

        if (wishlist.getProducts() != null) {
            dto.setTotalProducts(wishlist.getProducts().size());
            dto.setProducts(
                    wishlist.getProducts()
                            .stream()
                            .map(this::mapProduct)
                            .collect(Collectors.toSet()));

        }else {
            dto.setTotalProducts(0);
        }

        return dto;
    }


    private WishlistProductDto mapProduct(Product product) {
        WishlistProductDto dto =
                new WishlistProductDto();

        dto.setProductId(product.getId());
        dto.setProductName(product.getName());
        dto.setAverageRating(product.getAverageRating());
        dto.setTotalReviews(product.getTotalReviews());

        ProductVariant variant = product.getVariants()
                .stream()
                .findFirst()
                .orElse(null);

        if (variant != null) {
            dto.setPrice(variant.getPrice());
            dto.setDiscountPrice(variant.getDiscountPrice());
        }


        if (product.getImageUrls() != null &&
                !product.getImageUrls().isEmpty()) {

            dto.setImageUrl(
                    product.getImageUrls().get(0)
            );
        }

        return dto;
    }

}