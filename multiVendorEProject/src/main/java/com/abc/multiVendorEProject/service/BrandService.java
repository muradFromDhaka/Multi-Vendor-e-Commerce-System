package com.abc.multiVendorEProject.service;

import com.abc.multiVendorEProject.DTOs.projectDtos.BrandRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.BrandResponseDto;
import com.abc.multiVendorEProject.entity.Brand;
import com.abc.multiVendorEProject.mapper.BrandMapper;
import com.abc.multiVendorEProject.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final FileStorageService fileStorageService;

    public BrandResponseDto create(BrandRequestDto dto, MultipartFile logo) throws IOException {
        Brand brand = BrandMapper.toEntity(dto);

        // Handle logo upload
        if (logo != null && !logo.isEmpty()) {
            String fileName = fileStorageService.saveFile(logo);
            brand.setLogoUrl(fileName);
        }

        Brand saved = brandRepository.save(brand);
        return BrandMapper.toResponseDto(saved);
    }

    @Transactional
    public BrandResponseDto update(Long id, BrandRequestDto dto, MultipartFile logo) throws IOException {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        brand.setName(dto.getName());
        brand.setDescription(dto.getDescription());

        if (logo != null && !logo.isEmpty()) {
            if (brand.getLogoUrl() != null) {
                fileStorageService.deleteFile(brand.getLogoUrl());
            }
            String fileName = fileStorageService.saveFile(logo);
            brand.setLogoUrl(fileName);
        }

        Brand updated = brandRepository.save(brand);
        return BrandMapper.toResponseDto(updated);
    }

    @Transactional
    public void delete(Long id) throws IOException {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        // Delete logo file if exists
        if (brand.getLogoUrl() != null) {
            fileStorageService.deleteFile(brand.getLogoUrl());
        }

        brandRepository.delete(brand);
    }

    public BrandResponseDto getById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        return BrandMapper.toResponseDto(brand);
    }

    public List<BrandResponseDto> getAll() {

        return brandRepository.findAll()
                .stream()
                .map(BrandMapper::toResponseDto)
                .toList();
    }
}
