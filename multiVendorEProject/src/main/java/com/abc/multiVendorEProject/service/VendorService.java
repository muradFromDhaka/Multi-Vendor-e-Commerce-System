package com.abc.multiVendorEProject.service;

import com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto.OrderResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.OrderItemResponseDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorStatsDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorSummaryDto;
import com.abc.multiVendorEProject.entity.Vendor;
import com.abc.multiVendorEProject.enums.VendorStatus;
import com.abc.multiVendorEProject.mapper.OrderItemMapper;
import com.abc.multiVendorEProject.mapper.OrderMapper;
import com.abc.multiVendorEProject.mapper.VendorMapper;
import com.abc.multiVendorEProject.repository.OrderItemRepository;
import com.abc.multiVendorEProject.repository.ProductRepository;
import com.abc.multiVendorEProject.repository.UserRepository;
import com.abc.multiVendorEProject.repository.VendorRepository;
import com.abc.multiVendorEProject.entity.User;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class  VendorService {

    private final VendorRepository vendorRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final FileStorageService fileStorageService;

    // ---------------- CREATE ----------------
    public VendorResponseDto createVendor(
            VendorRequestDto dto,
            MultipartFile logo,
            MultipartFile banner) {

        String userName = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        System.out.println("Logged in username: " + userName);

        User user = userRepository.findById(userName)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (vendorRepository.existsByUser(user)) {
            throw new RuntimeException("This user already has a vendor");
        }

        Vendor vendor = VendorMapper.toEntity(dto, null);

        vendor.setUser(user);

        // Generate Unique Slug
        String baseSlug = SlugUtil.toSlug(dto.getShopName());
        String slug = baseSlug;
        int count = 1;

        while (vendorRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + count++;
        }

        vendor.setSlug(slug);

        // Upload Logo & Banner
        if (logo != null && !logo.isEmpty()) {
            String logoFileName = fileStorageService.saveFile(logo);
            vendor.setLogoUrl(logoFileName);
        }

        if (banner != null && !banner.isEmpty()) {
            String bannerFileName = fileStorageService.saveFile(banner);
            vendor.setBannerUrl(bannerFileName);
        }

        Vendor savedVendor = vendorRepository.save(vendor);

        return VendorMapper.toDto(savedVendor);
    }

    // ---------------- GET ALL ----------------
    public List<VendorResponseDto> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(VendorMapper::toDto)
                .collect(Collectors.toList());
    }

    // ---------------- GET BY ID ----------------
    public VendorResponseDto getVendorById(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found with id: " + id));

        return VendorMapper.toDto(vendor);
    }

    // ---------------- UPDATE ----------------
    public VendorResponseDto updateVendor(
            Long id,
            VendorRequestDto dto,
            MultipartFile logo,
            MultipartFile banner) {

        Vendor existing = vendorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Vendor not found with id: " + id));

        Vendor updated = VendorMapper.toEntity(dto, existing);

        // Logo
        if (logo != null && !logo.isEmpty()) {

            if (updated.getLogoUrl() != null) {
                fileStorageService.deleteFile(updated.getLogoUrl());
            }

            updated.setLogoUrl(fileStorageService.saveFile(logo));
        }

        // Banner
        if (banner != null && !banner.isEmpty()) {

            if (updated.getBannerUrl() != null) {
                fileStorageService.deleteFile(updated.getBannerUrl());
            }

            updated.setBannerUrl(fileStorageService.saveFile(banner));
        }

        Vendor savedVendor = vendorRepository.save(updated);

        return VendorMapper.toDto(savedVendor);
    }

    // ---------------- DELETE ----------------
    public void deleteVendor(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found with id: " + id));

        vendorRepository.delete(vendor);
    }

    // ---------------- GET VENDOR BY LOGGED-IN USER ----------------
    public VendorResponseDto getMyVendor() {
        Vendor vendor = getLoggedInVendor();
        return VendorMapper.toDto(vendor);
    }

    // ---------------- GET LOGGED-IN VENDOR ----------------
    public Vendor getLoggedInVendor() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return vendorRepository.findByUserUserName(username)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
    }

    // ---------------- GET ALL ORDER ITEMS OF LOGGED-IN VENDOR ----------------
    public List<OrderItemResponseDTO> getMyOrderItems() {
        Vendor vendor = getLoggedInVendor();
        return orderItemRepository.findByVendor(vendor)
                .stream()
                .map(OrderItemMapper::toResponseDto)
                .collect(Collectors.toList());
    }


    // ---------------- GET ALL ORDERS OF LOGGED-IN VENDOR ----------------
    public Page<OrderResponseDto> getMyOrders(Pageable pageable) {
        Vendor vendor = getLoggedInVendor();
        return orderItemRepository.findOrdersByVendorId(vendor.getId(),pageable)
                .map(OrderMapper::toResponseDto) ;// Order → OrderResponseDto
    }


//    -------------------------Vendor Status --------------------------

    public VendorResponseDto updateVendorStatus(Long id, VendorStatus status) {

        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        vendor.setStatus(status);

        Vendor savedVendor = vendorRepository.save(vendor);

        return VendorMapper.toDto(savedVendor);
    }


    public List<VendorResponseDto> getVendors(
            VendorStatus status,
            String search) {

        List<Vendor> vendors;

        if (status == null && (search == null || search.isBlank())) {

            vendors = vendorRepository.findAll();

        }

        else if (status != null && (search == null || search.isBlank())) {

            vendors = vendorRepository.findByStatus(status);

        }

        else if (status == null) {

            vendors = vendorRepository
                    .findByShopNameContainingIgnoreCase(search);

        }

        else {

            vendors = vendorRepository
                    .findByStatusAndShopNameContainingIgnoreCase(
                            status,
                            search
                    );

        }

        return vendors.stream()
                .map(VendorMapper::toDto)
                .toList();
    }



    public VendorSummaryDto getVendorSummary() {

        return new VendorSummaryDto(

                vendorRepository.count(),
                vendorRepository.countByStatus(VendorStatus.PENDING),
                vendorRepository.countByStatus(VendorStatus.APPROVED),
                vendorRepository.countByStatus(VendorStatus.ACTIVE),
                vendorRepository.countByStatus(VendorStatus.SUSPENDED),
                vendorRepository.countByStatus(VendorStatus.REJECTED)

        );

    }


    public Page<OrderResponseDto> getVendorOrders(
            Long vendorId,
            Pageable pageable) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() ->
                        new RuntimeException("Vendor not found"));

        return orderItemRepository
                .findOrdersByVendorId(vendor.getId(), pageable)
                .map(OrderMapper::toResponseDto);
    }


    public VendorStatsDto getVendorStats(Long vendorId) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() ->
                        new RuntimeException("Vendor not found"));

        return new VendorStatsDto(
                productRepository.countByVendorId(vendor.getId()),
                orderItemRepository.countOrders(vendor.getId()),
                orderItemRepository.getVendorRevenue(vendor.getId()),
                orderItemRepository.countCustomers(vendor.getId())
        );
    }


}
