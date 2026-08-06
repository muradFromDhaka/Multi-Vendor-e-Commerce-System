package com.abc.multiVendorEProject.repository;

import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.TopVendorDto;
import com.abc.multiVendorEProject.entity.Vendor;
import com.abc.multiVendorEProject.entity.VendorOrder;
import com.abc.multiVendorEProject.enums.VendorOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface VendorOrderRepository extends JpaRepository<VendorOrder, Long>{

    long countByVendor(Vendor vendor);

    long countByVendorAndVendorOrderStatus(
            Vendor vendor,
            VendorOrderStatus status
    );

    List<VendorOrder> findByOrderId(Long orderId);
    List<VendorOrder> findByVendorId(Long vendorId);
	Page<VendorOrder> findByVendorId(Long vendorId, Pageable pageable);

    List<VendorOrder> findByVendorIdAndVendorOrderStatus(Long vendorId, VendorOrderStatus status);

    long countByVendorId(Long vendorId);

    long countByVendorIdAndVendorOrderStatus(Long vendorId, VendorOrderStatus status);

    Page<VendorOrder> findByVendorOrderByCreatedAtDesc(
            Vendor vendor,
            Pageable pageable
    );

    Optional<VendorOrder> findByIdAndVendor(Long vendorOrderId, Vendor vendor);

    Page<VendorOrder> findByVendor(
            Vendor vendor,
            Pageable pageable);

    Page<VendorOrder> findByVendorAndVendorOrderStatus(
            Vendor vendor,
            VendorOrderStatus status,
            Pageable pageable);


// ============Top Vendor=======================
    @Query("""
select new com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.TopVendorDto(
    v.shopName,
    coalesce(sum(vo.totalPrice),0)
)
from VendorOrder vo
join vo.vendor v
group by v.id, v.shopName
order by sum(vo.totalPrice) desc
""")
    List<TopVendorDto> findTopVendor(Pageable pageable);


    @Query("""
SELECT COALESCE(SUM(vo.totalPrice),0)
FROM VendorOrder vo
WHERE vo.vendor = :vendor
AND vo.vendorOrderStatus='DELIVERED'
""")
    BigDecimal getVendorTotalRevenue(@Param("vendor") Vendor vendor);


    @Query("""
SELECT COALESCE(SUM(vo.totalPrice), 0)
FROM VendorOrder vo
WHERE vo.vendor = :vendor
AND vo.vendorOrderStatus = 'DELIVERED'
AND DATE(vo.createdAt) = CURRENT_DATE
""")
    BigDecimal getVendorTodayRevenue(@Param("vendor") Vendor vendor);


    @Query("""
SELECT COALESCE(SUM(vo.totalPrice), 0)
FROM VendorOrder vo
WHERE vo.vendor = :vendor
AND vo.vendorOrderStatus = 'DELIVERED'
AND YEAR(vo.createdAt) = YEAR(CURRENT_DATE)
AND MONTH(vo.createdAt) = MONTH(CURRENT_DATE)
""")
    BigDecimal getVendorMonthlyRevenue(@Param("vendor") Vendor vendor);

    @Query("""
SELECT COUNT(DISTINCT vo.order.user.id)
FROM VendorOrder vo
WHERE vo.vendor=:vendor
""")
    Long countDistinctCustomers(
            @Param("vendor") Vendor vendor
    );

    List<VendorOrder> findTop5ByVendorOrderByCreatedAtDesc(Vendor vendor);
}
