package com.abc.multiVendorEProject.Controller.Admin.VariantController;


import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.AttributeRequestDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.AttributeResponseDTO;
import com.abc.multiVendorEProject.service.VariantService.AttributeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/attributes")
@RequiredArgsConstructor
public class AttributeController {

    private final AttributeService attributeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AttributeResponseDTO createAttribute(
            @Valid @RequestBody AttributeRequestDTO dto) {

        return attributeService.createAttribute(dto);
    }

    @GetMapping
    public Page<AttributeResponseDTO> getAllAttributes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
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

    @PutMapping("/{id}")
    public AttributeResponseDTO updateAttribute(
            @PathVariable Long id,
            @Valid @RequestBody AttributeRequestDTO dto) {

        return attributeService.updateAttribute(id, dto);
    }


    @GetMapping("/category/{categoryId}")
    public Page<AttributeResponseDTO> getAttributeByCategory(
            @PathVariable Long categoryId,
            Pageable pageable) {

        return attributeService.getAttributeByCategory(categoryId,pageable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAttribute(
            @PathVariable Long id) {

        attributeService.deleteAttribute(id);
    }

}