package com.abc.multiVendorEProject.Controller.Vendor;

import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.ProductVariantRequestDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.ProductVariantResponseDTO;
import com.abc.multiVendorEProject.entity.Vendor;
import com.abc.multiVendorEProject.mapper.Variant.ProductVariantMapper;
import com.abc.multiVendorEProject.service.Vendor.VendorProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/vendor/product-variants")
@RequiredArgsConstructor
public class VendorProductVariantController {

    private final VendorProductVariantService vendorProductVariantService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductVariantResponseDTO createVariant(
            @RequestPart("variant") ProductVariantRequestDTO dto,
            @RequestPart(value = "images", required = false)
            List<MultipartFile> images) {

        return vendorProductVariantService.createVariant(dto, images);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductVariantResponseDTO updateVariant(
            @PathVariable Long id,
            @RequestPart("variant") ProductVariantRequestDTO dto,
            @RequestPart(value = "images", required = false)
            List<MultipartFile> images) {

        return vendorProductVariantService.updateVariant(id, dto, images);
    }


    @GetMapping
    public Page<ProductVariantResponseDTO> getAllVariants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        return vendorProductVariantService.getAllVariants(
                page,
                size,
                sortBy,
                sortDir
        );
    }

    @GetMapping("/{id}")
    public ProductVariantResponseDTO getVariantById(
            @PathVariable Long id) {

        return vendorProductVariantService.getVariantById(id);
    }

    @GetMapping("/product/{productId}")
    public Page<ProductVariantResponseDTO> getVariantsByProduct(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return vendorProductVariantService.getVariantsByProduct(
                productId,
                page,
                size
        );
    }

    @GetMapping("/sku/{sku}")
    public ProductVariantResponseDTO getVariantBySku(
            @PathVariable String sku) {

        return vendorProductVariantService.getVariantBySku(sku);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVariant(
            @PathVariable Long id) {

        vendorProductVariantService.deleteVariant(id);
    }
}