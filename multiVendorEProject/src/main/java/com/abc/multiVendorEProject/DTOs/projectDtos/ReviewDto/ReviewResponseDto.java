package com.abc.multiVendorEProject.DTOs.projectDtos.ReviewDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewResponseDto {

    private Long id;

    private String userName;

//    private String userImage;

    private Long productId;

    private String productName;

    private Integer rating;

    private String comment;

    private LocalDateTime createdAt;
}