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
    @Min('1')
    @Max('5')
    private Integer rating;

    @NotBlank
    @Size(max = 1000)
    private String comment;
}