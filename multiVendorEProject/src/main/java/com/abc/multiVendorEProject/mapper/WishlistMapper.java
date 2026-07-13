package com.abc.multiVendorEProject.mapper;

import com.abc.multiVendorEProject.DTOs.projectDtos.WishlistDto.WishlistProductDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.WishlistDto.WishlistResponseDto;
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

        dto.setTotalProducts(wishlist.getProducts().size());

        if (wishlist.getUser() != null) {
            dto.setUsername(
                    wishlist.getUser()
                            .getUserName());
        }

        if (wishlist.getProducts() != null) {
            dto.setProducts(
                    wishlist.getProducts()
                            .stream()
                            .map(product -> {

                                WishlistProductDto wishlistProductDto =
                                        new WishlistProductDto();

                                wishlistProductDto.setProductId(product.getId());
                                wishlistProductDto.setProductName(product.getName());
                                wishlistProductDto.setAverageRating(product.getAverageRating());
                                wishlistProductDto.setTotalReviews(product.getTotalReviews());

                                return wishlistProductDto;
                            })
                            .collect(Collectors.toSet()));
        }

        return dto;
    }
}