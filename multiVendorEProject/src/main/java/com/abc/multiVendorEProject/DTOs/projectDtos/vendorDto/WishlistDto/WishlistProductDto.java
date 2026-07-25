package com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.WishlistDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WishlistProductDto {

    private Long productId;

    private String productName;

    private String imageUrl;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private Double averageRating;

    private Integer totalReviews;
}