package com.abc.multiVendorEProject.repository;

import com.abc.multiVendorEProject.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    long count();
}
