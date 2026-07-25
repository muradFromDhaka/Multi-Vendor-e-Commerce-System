package com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.WishlistDto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class WishlistResponseDto {

    private Long wishlistId;

    private Integer totalProducts;

    private Set<WishlistProductDto> products;
}