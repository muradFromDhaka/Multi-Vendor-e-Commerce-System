package com.abc.multiVendorEProject.DTOs.projectDtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponseDto {

    private Long id;

    private String userName;

    private Long productId;

    private String productName;

    private Double rating;

    private String comment;
}