package com.abc.multiVendorEProject.mapper.Variant;

import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.AttributeRequestDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.AttributeResponseDTO;
import com.abc.multiVendorEProject.entity.Variant.Attribute;
import org.springframework.stereotype.Component;

@Component
public class AttributeMapper {

    public AttributeResponseDTO toResponse(Attribute attribute) {
        if (attribute == null) return null;

        AttributeResponseDTO dto = new AttributeResponseDTO(
                attribute.getId(),
                attribute.getName(),
                attribute.getCategory().getId(),
                attribute.getCategory().getName()
        );


        return dto;
    }

    public Attribute toEntity(AttributeRequestDTO dto) {
        if (dto == null) return null;

        Attribute attribute = new Attribute();
        attribute.setName(dto.name());

        return attribute;
    }
}