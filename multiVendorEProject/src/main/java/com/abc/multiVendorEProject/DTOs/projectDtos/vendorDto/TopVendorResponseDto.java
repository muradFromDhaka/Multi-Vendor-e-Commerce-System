package com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopVendorResponseDto {

    private Long id;
    private String shopName;
    private String logoUrl;
    private Double rating;
    private Long totalProducts;

}