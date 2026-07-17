package com.abc.multiVendorEProject.Controller.Vendor;

import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.AttributeValueResponseDTO;
import com.abc.multiVendorEProject.service.VariantService.AttributeValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor/attribute-values")
@RequiredArgsConstructor
public class VendorAttributeValueController {

    private final AttributeValueService attributeValueService;

    @GetMapping
    public Page<AttributeValueResponseDTO> getAllAttributeValues(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        return attributeValueService.getAllAttributeValues(
                page,
                size,
                sortBy,
                sortDir
        );
    }

    @GetMapping("/{id}")
    public AttributeValueResponseDTO getAttributeValueById(
            @PathVariable Long id) {

        return attributeValueService.getAttributeValueById(id);
    }

    @GetMapping("/attribute/{attributeId}")
    public Page<AttributeValueResponseDTO> getAttributeValuesByAttributeId(
            @PathVariable Long attributeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {

        return attributeValueService.getAttributeValuesByAttributeId(
                attributeId,
                page,
                size
        );
    }

}