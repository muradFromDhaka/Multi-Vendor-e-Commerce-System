package com.abc.multiVendorEProject.service.Vendor;

import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.ProductVariantRequestDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.ProductVariantResponseDTO;
import com.abc.multiVendorEProject.entity.Product;
import com.abc.multiVendorEProject.entity.Variant.AttributeValue;
import com.abc.multiVendorEProject.entity.Variant.ProductVariant;
import com.abc.multiVendorEProject.entity.Vendor;
import com.abc.multiVendorEProject.mapper.Variant.ProductVariantMapper;
import com.abc.multiVendorEProject.repository.ProductRepository;
import com.abc.multiVendorEProject.repository.VariantRepository.AttributeValueRepository;
import com.abc.multiVendorEProject.repository.VariantRepository.ProductVariantRepository;
import com.abc.multiVendorEProject.repository.VendorRepository;
import com.abc.multiVendorEProject.service.FileStorageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VendorProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;
    private final FileStorageService fileStorageService;
    private final VendorRepository vendorRepository;
    private final AttributeValueRepository attributeValueRepository;


    //    ==================Helper Class============================
    private Vendor getCurrentVendor() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return vendorRepository.findByUserUserName(username)
                .orElseThrow(() ->
                        new EntityNotFoundException("Vendor account not found"));
    }


    @Transactional
    public ProductVariantResponseDTO createVariant(
            ProductVariantRequestDTO dto,
            List<MultipartFile> images) {

        // SKU Validation
        if (productVariantRepository.existsBySku(dto.getSku())) {
            throw new RuntimeException(
                    "Variant SKU already exists: " + dto.getSku());
        }


        // Product Validation
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found with id: " + dto.getProductId()));

        // vendor validation
        Vendor currentVendor = getCurrentVendor();

        if (!product.getVendor().getId().equals(currentVendor.getId())) {
            throw new AccessDeniedException("You cannot add variants to this product.");
        }


        // Attribute Value Validation
        Set<AttributeValue> attributeValues = new HashSet<>(
                attributeValueRepository.findAllById(dto.getAttributeValueIds()));

        if (attributeValues.size() != dto.getAttributeValueIds().size()) {
            throw new RuntimeException(
                    "One or more attribute values are invalid.");
        }

        // Convert DTO → Entity
        ProductVariant variant = ProductVariantMapper.toEntity(dto);

        variant.setProduct(product);
        variant.setAttributeValues(attributeValues);

        // Existing Images (Useful for future update compatibility)
        List<String> imageUrls = new ArrayList<>();

        if (dto.getImageUrls() != null) {
            imageUrls.addAll(dto.getImageUrls());
        }

        // Upload New Images
        if (images != null && !images.isEmpty()) {

            List<String> uploadedImages = images.stream()
                    .filter(file -> !file.isEmpty())
                    .map(fileStorageService::saveFile)
                    .toList();

            imageUrls.addAll(uploadedImages);
        }

        variant.setImageUrls(imageUrls);

        ProductVariant savedVariant =
                productVariantRepository.save(variant);

        return ProductVariantMapper.toResponse(savedVariant);
    }


    @Transactional
    public ProductVariantResponseDTO updateVariant(
            Long id,
            ProductVariantRequestDTO dto,
            List<MultipartFile> images) {

        ProductVariant variant = productVariantRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Variant not found with id: " + id));

        Vendor currentVendor = getCurrentVendor();

        if (!variant.getProduct().getVendor().getId().equals(currentVendor.getId())) {
            throw new AccessDeniedException("You are not allowed to update this variant.");
        }

        // SKU Validation (নিজের SKU হলে Allow)
        if (!variant.getSku().equals(dto.getSku())
                && productVariantRepository.existsBySku(dto.getSku())) {

            throw new RuntimeException(
                    "Variant SKU already exists: " + dto.getSku());
        }

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found with id: " + dto.getProductId()));

        if (!product.getVendor().getId().equals(currentVendor.getId())) {
            throw new AccessDeniedException("Access denied.");
        }

        Set<AttributeValue> attributeValues = new HashSet<>(
                attributeValueRepository.findAllById(dto.getAttributeValueIds()));

        if (attributeValues.size() != dto.getAttributeValueIds().size()) {
            throw new RuntimeException(
                    "One or more attribute values are invalid.");
        }

        variant.setSku(dto.getSku());
        variant.setPrice(dto.getPrice());
        variant.setDiscountPrice(dto.getDiscountPrice());
        variant.setStock(dto.getStock());

        variant.setProduct(product);
        variant.setAttributeValues(attributeValues);

        // Existing images that user wants to keep
        List<String> imageUrls = new ArrayList<>();

        if (dto.getImageUrls() != null) {
            imageUrls.addAll(dto.getImageUrls());
        }

// Upload new images
        if (images != null && !images.isEmpty()) {

            List<String> uploadedImages = images.stream()
                    .filter(file -> !file.isEmpty())
                    .map(fileStorageService::saveFile)
                    .toList();

            imageUrls.addAll(uploadedImages);
        }

// Delete removed images from disk
        List<String> oldImages = variant.getImageUrls();

        if (oldImages != null) {

            for (String image : oldImages) {

                if (!imageUrls.contains(image)) {

                    String fileName = Paths.get(image)
                            .getFileName()
                            .toString();

                    fileStorageService.deleteFile(fileName);
                }
            }
        }

        // Update entity
        variant.setImageUrls(imageUrls);

        ProductVariant updated = productVariantRepository.save(variant);

        return ProductVariantMapper.toResponse(updated);
    }


    public Page<ProductVariantResponseDTO> getAllVariants(
            int page,
            int size,
            String sortBy,
            String sortDir) {

        Vendor currentVendor = getCurrentVendor();

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return productVariantRepository
                .findByProductVendorId(currentVendor.getId(), pageable)
                .map(ProductVariantMapper::toResponse);
    }



    public ProductVariantResponseDTO getVariantById(Long id) {

        ProductVariant variant =
                productVariantRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Variant not found with id: " + id));

        Vendor currentVendor = getCurrentVendor();

        if (!variant.getProduct().getVendor().getId().equals(currentVendor.getId())) {
            throw new AccessDeniedException("Access denied.");
        }


        return ProductVariantMapper.toResponse(variant);
    }

    public Page<ProductVariantResponseDTO> getVariantsByProduct(
            Long productId,
            int page,
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new EntityNotFoundException("Product not found with id "+productId));

        Vendor currentVendor = getCurrentVendor();

        if (!product.getVendor().getId().equals(currentVendor.getId())) {
            throw new AccessDeniedException("Access denied.");
        }

        return productVariantRepository
                .findByProductId(productId, pageable)
                .map(ProductVariantMapper::toResponse);
    }


    public ProductVariantResponseDTO getVariantBySku(String sku) {

        ProductVariant variant = productVariantRepository
                .findBySku(sku)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Variant not found with SKU: " + sku));

        Vendor currentVendor = getCurrentVendor();

        if (!variant.getProduct().getVendor().getId().equals(currentVendor.getId())) {
            throw new AccessDeniedException("Access denied.");
        }

        return ProductVariantMapper.toResponse(variant);
    }



    @Transactional
    public void deleteVariant(Long id) {

        ProductVariant variant = productVariantRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Variant not found with id: " + id));

        Vendor currentVendor = getCurrentVendor();

        if (!variant.getProduct().getVendor().getId().equals(currentVendor.getId())) {
            throw new AccessDeniedException("Access denied.");
        }

        if (variant.getImageUrls() != null) {

            variant.getImageUrls()
                    .forEach(fileStorageService::deleteFileByPath);
        }
        productVariantRepository.delete(variant);
    }

}