package com.abc.multiVendorEProject.DTOs.projectDtos.BannerDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BannerRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    private String subtitle;

    @NotBlank(message = "Button text is required")
    private String buttonText;

    private String buttonLink;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    private Integer displayOrder;

    private Boolean active;
}