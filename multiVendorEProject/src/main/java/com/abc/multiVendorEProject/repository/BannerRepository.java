package com.abc.multiVendorEProject.repository;

import com.abc.multiVendorEProject.entity.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;

public interface BannerRepository extends JpaRepository<Banner,Long> {


    Page<Banner> findByActiveTrueOrderByDisplayOrderAsc(Pageable pageable);
}
