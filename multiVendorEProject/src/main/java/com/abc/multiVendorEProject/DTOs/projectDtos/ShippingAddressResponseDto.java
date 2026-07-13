package com.abc.multiVendorEProject.DTOs.projectDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddressResponseDto {

    private Long id;

    private String customerName;

    private String phone;

    private String street;

    private String city;

    private String district;

    private String country;

    private String zipCode;
}