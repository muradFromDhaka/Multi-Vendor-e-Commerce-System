package com.abc.multiVendorEProject.mapper;

import com.abc.multiVendorEProject.DTOs.projectDtos.UserProductViewResponseDto;
import com.abc.multiVendorEProject.entity.UserProductView;

public class UserProductViewMapper {

    public static UserProductViewResponseDto toDto(
            UserProductView view) {

        UserProductViewResponseDto dto =
                new UserProductViewResponseDto();

        dto.setId(view.getId());

        dto.setUserName(
                view.getUser().getUserName());

        dto.setProductId(
                view.getProduct().getId());

        dto.setProductName(
                view.getProduct().getName());

        dto.setViewedAt(
                view.getViewedAt());

        return dto;
    }
}