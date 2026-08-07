package com.abc.multiVendorEProject.Controller.Admin;

import com.abc.multiVendorEProject.DTOs.projectDtos.BannerDto.BannerRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.BannerDto.BannerResponseDto;
import com.abc.multiVendorEProject.service.Admin.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/banners")
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    // ==========================
    // Create Banner
    // ==========================
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BannerResponseDto> createBanner(

            @ModelAttribute BannerRequestDto dto,

            @RequestParam("image")
            MultipartFile image
    ) {

        return ResponseEntity.ok(
                bannerService.createBanner(dto, image)
        );
    }

    // ==========================
    // Update Banner
    // ==========================
    @PutMapping(value = "/{bannerId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BannerResponseDto> updateBanner(

            @PathVariable Long bannerId,

            @ModelAttribute BannerRequestDto dto,

            @RequestParam(value = "image", required = false)
            MultipartFile image
    ) {

        return ResponseEntity.ok(
                bannerService.updateBanner(
                        bannerId,
                        dto,
                        image
                )
        );
    }

    // ==========================
    // Delete Banner
    // ==========================
    @DeleteMapping("/{bannerId}")
    public ResponseEntity<String> deleteBanner(
            @PathVariable Long bannerId
    ) {

        bannerService.deleteBanner(bannerId);

        return ResponseEntity.ok(
                "Banner deleted successfully."
        );
    }

    // ==========================
    // Get Banner By Id
    // ==========================
    @GetMapping("/{bannerId}")
    public ResponseEntity<BannerResponseDto> getBanner(
            @PathVariable Long bannerId
    ) {

        return ResponseEntity.ok(
                bannerService.getBanner(bannerId)
        );
    }

    // ==========================
    // Get All Banners
    // ==========================
    @GetMapping
    public ResponseEntity<List<BannerResponseDto>> getAllBanners() {

        return ResponseEntity.ok(
                bannerService.getAllBanners()
        );
    }

    // ==========================
    // Get Active Banners
    // ==========================
    @GetMapping("/active")
    public ResponseEntity<Page<BannerResponseDto>> getActiveBanners(
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                bannerService.getActiveBanners(pageable)
        );
    }

}