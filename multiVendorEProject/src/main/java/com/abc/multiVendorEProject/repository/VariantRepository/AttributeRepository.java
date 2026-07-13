package com.abc.multiVendorEProject.repository.VariantRepository;

import com.abc.multiVendorEProject.entity.Variant.Attribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    Page<Attribute> findByCategoryId(Long categoryId, Pageable pageable);

    boolean existsByCategoryIdAndName(Long categoryId, String name);

    boolean existsByCategoryIdAndNameAndIdNot(Long categoryId, String name, Long id);

}
