package com.abc.multiVendorEProject.service.Admin;

import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.ProductVariantResponseDTO;
import com.abc.multiVendorEProject.entity.Variant.ProductVariant;
import com.abc.multiVendorEProject.mapper.Variant.ProductVariantMapper;
import com.abc.multiVendorEProject.repository.VariantRepository.ProductVariantRepository;
import com.abc.multiVendorEProject.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final FileStorageService fileStorageService;


    public Page<ProductVariantResponseDTO> getAllVariants(
            int page,
            int size,
            String sortBy,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        return productVariantRepository.findAll(pageable)
                .map(ProductVariantMapper::toResponse);
    }

    public ProductVariantResponseDTO getVariantById(Long id) {

        ProductVariant variant =
                productVariantRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Variant not found with id: " + id));

        return ProductVariantMapper.toResponse(variant);
    }

    public Page<ProductVariantResponseDTO> getVariantsByProduct(
            Long productId,
            int page,
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

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

        return ProductVariantMapper.toResponse(variant);
    }



    @Transactional
    public void deleteVariant(Long id) {

        ProductVariant variant = productVariantRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Variant not found with id: " + id));
        if (variant.getImageUrls() != null) {

            variant.getImageUrls()
                    .forEach(fileStorageService::deleteFileByPath);
        }
        productVariantRepository.delete(variant);
    }
}