package com.abc.multiVendorEProject.Controller.Admin.VariantController;

import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.ProductVariantRequestDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.ProductVariantResponseDTO;
import com.abc.multiVendorEProject.service.Admin.AdminProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/admin/product-variants")
@RequiredArgsConstructor
public class AdminProductVariantController {

    private final AdminProductVariantService adminProductVariantService;


    @GetMapping
    public Page<ProductVariantResponseDTO> getAllVariants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        return adminProductVariantService.getAllVariants(
                page,
                size,
                sortBy,
                sortDir
        );
    }

    @GetMapping("/{id}")
    public ProductVariantResponseDTO getVariantById(
            @PathVariable Long id) {

        return adminProductVariantService.getVariantById(id);
    }

    @GetMapping("/product/{productId}")
    public Page<ProductVariantResponseDTO> getVariantsByProduct(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return adminProductVariantService.getVariantsByProduct(
                productId,
                page,
                size
        );
    }


    @GetMapping("/sku/{sku}")
    public ProductVariantResponseDTO getVariantBySku(
            @PathVariable String sku) {

        return adminProductVariantService.getVariantBySku(sku);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVariant(
            @PathVariable Long id) {

        adminProductVariantService.deleteVariant(id);
    }
}