package com.abc.multiVendorEProject.DTOs.projectDtos.WishlistDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishlistProductDto {

    private Long productId;

    private String productName;

    private Double averageRating;

    private Integer totalReviews;
}