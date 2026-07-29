package com.abc.multiVendorEProject.DTOs.projectDtos.BannerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BannerResponseDto {

    private Long id;

    private String title;

    private String subtitle;

    private String buttonText;

    private String buttonLink;

    private String imageUrl;

    private Integer displayOrder;

    private Boolean active;
}