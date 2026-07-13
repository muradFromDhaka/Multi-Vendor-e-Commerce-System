package com.abc.multiVendorEProject.service.VariantService;

import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.AttributeRequestDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.AttributeResponseDTO;
import com.abc.multiVendorEProject.Util.DuplicateResourceException;
import com.abc.multiVendorEProject.entity.Category;
import com.abc.multiVendorEProject.entity.Variant.Attribute;
import com.abc.multiVendorEProject.mapper.Variant.AttributeMapper;
import com.abc.multiVendorEProject.repository.CategoryRepository;
import com.abc.multiVendorEProject.repository.VariantRepository.AttributeRepository;
import com.abc.multiVendorEProject.repository.VariantRepository.AttributeValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttributeService {

    private final AttributeRepository attributeRepository;
    private final AttributeMapper attributeMapper;
    private final CategoryRepository categoryRepository;
    private final AttributeValueRepository attributeValueRepository;

    public AttributeResponseDTO createAttribute(AttributeRequestDTO dto) {

        if (attributeRepository.existsByCategoryIdAndName(
                dto.categoryId(),
                dto.name())) {

            throw new DuplicateResourceException(
                    "Attribute already exists for this category.");
        }

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));

        Attribute attribute = new Attribute();

        attribute.setName(dto.name().trim());

        attribute.setCategory(category);

        Attribute savedAttribute = attributeRepository.save(attribute);

        return attributeMapper.toResponse(savedAttribute);
    }


    public AttributeResponseDTO updateAttribute(
            Long id,
            AttributeRequestDTO dto) {

        if (attributeRepository.existsByCategoryIdAndNameAndIdNot(
                dto.categoryId(),
                dto.name(),
                id)) {

            throw new DuplicateResourceException(
                    "Attribute already exists for this category.");
        }

        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Attribute not found with id: " + id));

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found with id: " + dto.categoryId()));

        attribute.setName(dto.name().trim());

        attribute.setCategory(category);

        Attribute updated = attributeRepository.save(attribute);

        return attributeMapper.toResponse(updated);
    }


    public Page<AttributeResponseDTO> getAllAttributes(int page,int size,String sortBy,String sortDir) {
          Sort sort = sortDir.equalsIgnoreCase("desc")
                  ? Sort.by(sortBy).descending()
                  : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page,size,sort);

        return attributeRepository.findAll(pageable)
                .map(attributeMapper::toResponse);
    }


    public AttributeResponseDTO getAttributeById(Long id) {

        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Attribute not found with id: " + id));

        return attributeMapper.toResponse(attribute);
    }


    public Page<AttributeResponseDTO> getAttributeByCategory(Long categoryId,Pageable pageable) {

        return attributeRepository.findByCategoryId(categoryId,pageable)
                .map(attributeMapper::toResponse);
    }


    public void deleteAttribute(Long id) {

        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Attribute not found with id: " + id));

        long count = attributeValueRepository.countByAttributeId(id);

        if (count > 0) {

            throw new RuntimeException(
                    "This attribute contains values. Delete values first."
            );
        }

        attributeRepository.delete(attribute);
    }
}