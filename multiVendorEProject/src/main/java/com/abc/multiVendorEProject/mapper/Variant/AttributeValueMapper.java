package com.abc.multiVendorEProject.mapper.Variant;

import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.AttributeValueRequestDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.AttributeValueResponseDTO;
import com.abc.multiVendorEProject.entity.Variant.AttributeValue;
import org.springframework.stereotype.Component;

@Component
public class AttributeValueMapper {

    public static AttributeValueResponseDTO toResponse(AttributeValue attributeValue) {

        if (attributeValue == null) {
            return null;
        }

        AttributeValueResponseDTO dto = new AttributeValueResponseDTO(
                attributeValue.getId(),
                attributeValue.getValue(),
                attributeValue.getAttribute().getId(),
                attributeValue.getAttribute().getName()
        );

        return dto;
    }

    public static AttributeValue toEntity(AttributeValueRequestDTO dto) {

        if (dto == null) {
            return null;
        }

        AttributeValue attributeValue = new AttributeValue();
        attributeValue.setValue(dto.value());

        return attributeValue;
    }
}
