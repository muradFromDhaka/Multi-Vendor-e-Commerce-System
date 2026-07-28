package com.abc.multiVendorEProject.DTOs.projectDtos.ReviewDto;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDto {

    @NotNull
    private Long productId;

    @NotNull
    @DecimalMin("1.0")
    @DecimalMax("5.0")
    private Double rating;

    @NotBlank
    @Size(max = 1000)
    private String comment;
}