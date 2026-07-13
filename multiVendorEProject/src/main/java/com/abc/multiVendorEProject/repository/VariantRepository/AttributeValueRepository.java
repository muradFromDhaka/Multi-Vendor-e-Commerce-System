package com.abc.multiVendorEProject.repository.VariantRepository;

import com.abc.multiVendorEProject.entity.Variant.AttributeValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeValueRepository extends JpaRepository<AttributeValue, Long> {

    Page<AttributeValue> findByAttributeId(
            Long attributeId,
            Pageable pageable
    );

    long countByAttributeId(Long attributeId);
}
