package com.abc.multiVendorEProject.repository;

import com.abc.multiVendorEProject.entity.Vendor;
import com.abc.multiVendorEProject.entity.VendorOrder;
import com.abc.multiVendorEProject.enums.VendorOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VendorOrderRepository extends JpaRepository<VendorOrder, Long>{

    List<VendorOrder> findByOrderId(Long orderId);
    List<VendorOrder> findByVendorId(Long vendorId);
	Page<VendorOrder> findByVendorId(Long vendorId, Pageable pageable);

    List<VendorOrder> findByVendorIdAndStatus(Long vendorId, VendorOrderStatus status);

    long countByVendorId(Long vendorId);

    long countByVendorIdAndStatus(Long vendorId, VendorOrderStatus status);

    Page<VendorOrder> findByVendorOrderByCreatedAtDesc(
            Vendor vendor,
            Pageable pageable
    );

    Optional<VendorOrder> findByIdAndVendor(Long vendorOrderId, Vendor vendor);

    Page<VendorOrder> findByVendor(
            Vendor vendor,
            Pageable pageable);

    Page<VendorOrder> findByVendorAndStatus(
            Vendor vendor,
            VendorOrderStatus status,
            Pageable pageable);

}
