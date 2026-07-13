package com.abc.multiVendorEProject.DTOs.projectDtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDto {

    private Long productId;

    private Double rating;

    private String comment;
}