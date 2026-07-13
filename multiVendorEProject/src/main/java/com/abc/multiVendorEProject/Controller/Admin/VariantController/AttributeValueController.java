package com.abc.multiVendorEProject.Controller.Admin.VariantController;

import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.AttributeValueRequestDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.AttributeValueResponseDTO;
import com.abc.multiVendorEProject.service.VariantService.AttributeValueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/attribute-values")
@RequiredArgsConstructor
public class AttributeValueController {

    private final AttributeValueService attributeValueService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AttributeValueResponseDTO createAttributeValue(
            @Valid @RequestBody AttributeValueRequestDTO dto) {

        return attributeValueService.createAttributeValue(dto);
    }

    @GetMapping
    public Page<AttributeValueResponseDTO> getAllAttributeValues(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
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
    public Page<AttributeValueResponseDTO> getByAttributeId(
            @PathVariable Long attributeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return attributeValueService.getByAttributeId(
                attributeId,
                page,
                size
        );
    }

    @PutMapping("/{id}")
    public AttributeValueResponseDTO updateAttributeValue(
            @PathVariable Long id,
            @Valid @RequestBody AttributeValueRequestDTO dto) {

        return attributeValueService.updateAttributeValue(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAttributeValue(
            @PathVariable Long id) {

        attributeValueService.deleteAttributeValue(id);
    }
}