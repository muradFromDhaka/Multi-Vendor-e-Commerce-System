package com.abc.multiVendorEProject.DTOs.projectDtos.ReviewDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewSummaryDto {

    private Double averageRating;

    private Long totalReviews;

    private Long fiveStar;

    private Long fourStar;

    private Long threeStar;

    private Long twoStar;

    private Long oneStar;

}
