package com.abc.multiVendorEProject.Controller.Vendor.variantController;

import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.AttributeResponseDTO;
import com.abc.multiVendorEProject.service.VariantService.AttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor/attributes")
@RequiredArgsConstructor
public class VendorAttributeController {

    private final AttributeService attributeService;

    @GetMapping
    public Page<AttributeResponseDTO> getAllAttributes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        return attributeService.getAllAttributes(
                page,
                size,
                sortBy,
                sortDir
        );
    }

    @GetMapping("/{id}")
    public AttributeResponseDTO getAttributeById(
            @PathVariable Long id) {

        return attributeService.getAttributeById(id);
    }

    @GetMapping("/category/{categoryId}")
    public Page<AttributeResponseDTO> getByCategory(
            @PathVariable Long categoryId,
            Pageable pageable) {

        return attributeService.getAttributeByCategory(categoryId,pageable);
    }

}
