package com.abc.multiVendorEProject.repository;

import com.abc.multiVendorEProject.entity.User;
import com.abc.multiVendorEProject.entity.Vendor;
import com.abc.multiVendorEProject.enums.VendorStatus;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
