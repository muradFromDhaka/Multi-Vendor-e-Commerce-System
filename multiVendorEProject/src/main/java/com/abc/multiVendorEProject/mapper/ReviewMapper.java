package com.abc.multiVendorEProject.mapper;

import com.abc.multiVendorEProject.DTOs.projectDtos.ReviewRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.ReviewResponseDto;
import com.abc.multiVendorEProject.entity.Product;
import com.abc.multiVendorEProject.entity.Review;
import com.abc.multiVendorEProject.entity.User;

public class ReviewMapper {


    public static Review toEntity(ReviewRequestDto dto, User user, Product product ) {

        Review review = new Review();

        review.setUser(user);
        review.setProduct(product);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        return review;
    }

    public static ReviewResponseDto toDto(Review review) {

        ReviewResponseDto dto = new ReviewResponseDto();

        dto.setId(review.getId());

        dto.setUserName(
                review.getUser().getUserName());

        dto.setProductId(
                review.getProduct().getId());

        dto.setProductName(
                review.getProduct().getName());

        dto.setRating(review.getRating());

        dto.setComment(review.getComment());

        return dto;
    }
}