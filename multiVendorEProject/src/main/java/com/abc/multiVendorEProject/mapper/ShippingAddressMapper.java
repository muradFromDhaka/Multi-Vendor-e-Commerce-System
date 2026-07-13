package com.abc.multiVendorEProject.mapper;

import com.abc.multiVendorEProject.DTOs.projectDtos.ShippingAddressRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.ShippingAddressResponseDto;
import com.abc.multiVendorEProject.entity.ShippingAddress;
import org.springframework.stereotype.Component;

@Component
public class ShippingAddressMapper {

    public static ShippingAddress toEntity(ShippingAddressRequestDto dto) {

        if (dto == null) {
            return null;
        }

        ShippingAddress shippingAddress = new ShippingAddress();

        shippingAddress.setCustomerName(dto.getCustomerName());
        shippingAddress.setPhone(dto.getPhone());
        shippingAddress.setStreet(dto.getStreet());
        shippingAddress.setCity(dto.getCity());
        shippingAddress.setDistrict(dto.getDistrict());
        shippingAddress.setCountry(dto.getCountry());
        shippingAddress.setZipCode(dto.getZipCode());

        return shippingAddress;
    }

    public static ShippingAddressResponseDto toResponseDto(ShippingAddress entity) {

        if (entity == null) {
            return null;
        }

        return new ShippingAddressResponseDto(
                entity.getId(),
                entity.getCustomerName(),
                entity.getPhone(),
                entity.getStreet(),
                entity.getCity(),
                entity.getDistrict(),
                entity.getCountry(),
                entity.getZipCode()
        );
    }

    public static void updateEntity(ShippingAddress entity, ShippingAddressRequestDto dto) {

        if (entity == null || dto == null) {
            return;
        }

        entity.setCustomerName(dto.getCustomerName());
        entity.setPhone(dto.getPhone());
        entity.setStreet(dto.getStreet());
        entity.setCity(dto.getCity());
        entity.setDistrict(dto.getDistrict());
        entity.setCountry(dto.getCountry());
        entity.setZipCode(dto.getZipCode());
    }
}