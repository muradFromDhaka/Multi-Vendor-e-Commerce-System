package com.abc.multiVendorEProject.DTOs.projectDtos.WishlistDto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class WishlistResponseDto {

    private Long wishlistId;

    private String username;

    private Integer totalProducts;

    private Set<WishlistProductDto> products;
}