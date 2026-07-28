package com.abc.multiVendorEProject.repository;

import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.TopVendorResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorResponseDto;
import com.abc.multiVendorEProject.entity.User;
import com.abc.multiVendorEProject.entity.Vendor;
import com.abc.multiVendorEProject.enums.VendorStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Long>{

    long count();

    // Find vendor by user
    Optional<Vendor> findByUserUserName(String user);

    Optional<Vendor> findByUserEmail(String email);

    // Check if vendor exists for a user (used in create)
    boolean existsByUser(User user);

    // Check if slug already exists
    boolean existsBySlug(String slug);

    List<Vendor> findByStatus(VendorStatus status);

    List<Vendor> findByShopNameContainingIgnoreCase(String search);

    List<Vendor> findByStatusAndShopNameContainingIgnoreCase(
            VendorStatus status,
            String search
    );

    long countByStatus(VendorStatus status);

    Long countByCreatedAtBetween(LocalDateTime todayStart, LocalDateTime todayEnd);


    @Query("""
SELECT new com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.TopVendorResponseDto(

    v.id,
    v.shopName,
    v.logoUrl,
    COALESCE(v.rating, 0),
    COUNT(p.id)

)

FROM Vendor v
LEFT JOIN Product p
ON p.vendor.id = v.id

WHERE v.status = com.abc.multiVendorEProject.enums.VendorStatus.ACTIVE

GROUP BY
v.id,
v.shopName,
v.logoUrl,
v.rating

ORDER BY
COUNT(p.id) DESC,
COALESCE(v.rating, 0) DESC

""")
    Page<TopVendorResponseDto> getTopVendors(Pageable pageable);


}
