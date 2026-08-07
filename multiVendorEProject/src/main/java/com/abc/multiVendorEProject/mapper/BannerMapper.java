package com.abc.multiVendorEProject.mapper;

import com.abc.multiVendorEProject.DTOs.projectDtos.BannerDto.BannerRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.BannerDto.BannerResponseDto;
import com.abc.multiVendorEProject.entity.Banner;
import org.springframework.stereotype.Component;

@Component
public class BannerMapper {

    // Entity -> DTO
    public BannerResponseDto toDto(Banner banner) {

        if (banner == null) {
            return null;
        }

        BannerResponseDto dto = new BannerResponseDto();

        dto.setId(banner.getId());
        dto.setTitle(banner.getTitle());
        dto.setSubtitle(banner.getSubtitle());
        dto.setButtonText(banner.getButtonText());
        dto.setButtonLink(banner.getButtonLink());
        dto.setImageUrl(banner.getImageUrl());
        dto.setDisplayOrder(banner.getDisplayOrder());
        dto.setActive(banner.getActive());

        return dto;
    }

    // RequestDTO -> Entity
    public Banner toEntity(BannerRequestDto dto) {

        if (dto == null) {
            return null;
        }

        Banner banner = new Banner();

        banner.setTitle(dto.getTitle());
        banner.setSubtitle(dto.getSubtitle());
        banner.setButtonText(dto.getButtonText());
        banner.setButtonLink(dto.getButtonLink());
//        banner.setImageUrl(dto.getImageUrl());
        banner.setDisplayOrder(dto.getDisplayOrder());
        banner.setActive(dto.getActive());

        return banner;
    }

    // Update Entity
    public void updateEntity(Banner banner, BannerRequestDto dto) {

        banner.setTitle(dto.getTitle());
        banner.setSubtitle(dto.getSubtitle());
        banner.setButtonText(dto.getButtonText());
        banner.setButtonLink(dto.getButtonLink());
//        banner.setImageUrl(dto.getImageUrl());
        banner.setDisplayOrder(dto.getDisplayOrder());
        banner.setActive(dto.getActive());
    }

}