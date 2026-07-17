package com.abc.multiVendorEProject.service.VariantService;


import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.AttributeValueRequestDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.AttributeValueResponseDTO;
import com.abc.multiVendorEProject.entity.Variant.Attribute;
import com.abc.multiVendorEProject.entity.Variant.AttributeValue;
import com.abc.multiVendorEProject.mapper.Variant.AttributeValueMapper;
import com.abc.multiVendorEProject.repository.VariantRepository.AttributeRepository;
import com.abc.multiVendorEProject.repository.VariantRepository.AttributeValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AttributeValueService {

    private final AttributeValueRepository attributeValueRepository;
    private final AttributeRepository attributeRepository;

    @Transactional
    public AttributeValueResponseDTO createAttributeValue(AttributeValueRequestDTO dto) {

        Attribute attribute = attributeRepository.findById(dto.attributeId())
                .orElseThrow(() ->
                        new RuntimeException("Attribute not found with id: " + dto.attributeId()));

        AttributeValue attributeValue = AttributeValueMapper.toEntity(dto);
        attributeValue.setAttribute(attribute);

        AttributeValue saved = attributeValueRepository.save(attributeValue);

        return AttributeValueMapper.toResponse(saved);
    }

    public Page<AttributeValueResponseDTO> getAllAttributeValues(
            int page,
            int size,
            String sortBy,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return attributeValueRepository.findAll(pageable)
                .map(AttributeValueMapper::toResponse);
    }

    public AttributeValueResponseDTO getAttributeValueById(Long id) {

        AttributeValue attributeValue = attributeValueRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("AttributeValue not found with id: " + id));

        return AttributeValueMapper.toResponse(attributeValue);
    }

    public Page<AttributeValueResponseDTO> getAttributeValuesByAttributeId(
            Long attributeId,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);

        return attributeValueRepository
                .findByAttributeId(attributeId, pageable)
                .map(AttributeValueMapper::toResponse);
    }

    @Transactional
    public AttributeValueResponseDTO updateAttributeValue(
            Long id,
            AttributeValueRequestDTO dto) {

        AttributeValue attributeValue = attributeValueRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("AttributeValue not found with id: " + id));

        Attribute attribute = attributeRepository.findById(dto.attributeId())
                .orElseThrow(() ->
                        new RuntimeException("Attribute not found with id: " + dto.attributeId()));

        attributeValue.setValue(dto.value());
        attributeValue.setAttribute(attribute);

        AttributeValue updated = attributeValueRepository.save(attributeValue);

        return AttributeValueMapper.toResponse(updated);
    }

    @Transactional
    public void deleteAttributeValue(Long id) {

        AttributeValue attributeValue = attributeValueRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("AttributeValue not found with id: " + id));

        attributeValueRepository.delete(attributeValue);
    }
}