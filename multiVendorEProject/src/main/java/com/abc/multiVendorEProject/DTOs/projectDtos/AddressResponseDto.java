package com.abc.multiVendorEProject.DTOs.projectDtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddressResponseDto {
    private Long id;

    private String userName;

    private String street;

    private String city;

    private String district;

    private String country;

    private String zipCode;

}
