package com.abc.multiVendorEProject.service.Admin;

import com.abc.multiVendorEProject.DTOs.projectDtos.BannerDto.BannerRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.BannerDto.BannerResponseDto;
import com.abc.multiVendorEProject.entity.Banner;
import com.abc.multiVendorEProject.mapper.BannerMapper;
import com.abc.multiVendorEProject.repository.BannerRepository;
import com.abc.multiVendorEProject.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BannerService {

    private final BannerRepository bannerRepository;
    private final BannerMapper bannerMapper;
    private final FileStorageService fileStorageService;

    public BannerResponseDto createBanner(
            BannerRequestDto dto,
            MultipartFile image) {

        Banner banner = bannerMapper.toEntity(dto);

        if (image != null && !image.isEmpty()) {

            String imagePath = fileStorageService.saveFile(image);
            banner.setImageUrl(imagePath);
        }

        banner = bannerRepository.save(banner);

        return bannerMapper.toDto(banner);
    }

    public BannerResponseDto updateBanner(
            Long bannerId,
            BannerRequestDto dto,
            MultipartFile image) {

        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() ->
                        new RuntimeException("Banner not found"));

        bannerMapper.updateEntity(banner, dto);

        if (image != null && !image.isEmpty()) {

            if (banner.getImageUrl() != null) {
                fileStorageService.deleteFileByPath(
                        banner.getImageUrl());
            }

            String imagePath = fileStorageService.saveFile(image);
            banner.setImageUrl(imagePath);
        }

        banner = bannerRepository.save(banner);

        return bannerMapper.toDto(banner);
    }

    public void deleteBanner(Long bannerId) {

        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() ->
                        new RuntimeException("Banner not found"));

        if (banner.getImageUrl() != null) {
            fileStorageService.deleteFileByPath(
                    banner.getImageUrl());
        }

        bannerRepository.delete(banner);
    }

    @Transactional(readOnly = true)
    public BannerResponseDto getBanner(Long bannerId) {

        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() ->
                        new RuntimeException("Banner not found"));

        return bannerMapper.toDto(banner);
    }

    @Transactional(readOnly = true)
    public List<BannerResponseDto> getAllBanners() {

        return bannerRepository.findAll()
                .stream()
                .map(bannerMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<BannerResponseDto> getActiveBanners(Pageable pageable) {

        return bannerRepository
                .findByActiveTrueOrderByDisplayOrderAsc(pageable)
                .map(bannerMapper::toDto);
    }
}
