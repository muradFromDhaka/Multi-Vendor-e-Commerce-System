package com.abc.multiVendorEProject.service.Vendor;

import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.UpdateVendorOrderStatusRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorOrderDetailsResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorOrderListResponseDto;
import com.abc.multiVendorEProject.entity.Vendor;
import com.abc.multiVendorEProject.entity.VendorOrder;
import com.abc.multiVendorEProject.enums.OrderStatus;
import com.abc.multiVendorEProject.mapper.VendorOrderMapper;
import com.abc.multiVendorEProject.repository.OrderItemRepository;
import com.abc.multiVendorEProject.repository.VendorOrderRepository;
import com.abc.multiVendorEProject.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Transactional
@Service
public class VendorOrderService {


    private final VendorOrderRepository vendorOrderRepository;
    private final VendorRepository vendorRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional(readOnly = true)
    public Page<VendorOrderListResponseDto> getVendorOrders(Pageable pageable) {

        Vendor vendor = getLoggedInVendor();

        return vendorOrderRepository
                .findByVendorOrderByCreatedAtDesc(vendor, pageable)
                .map(VendorOrderMapper::toListDto);
    }


    @Transactional(readOnly = true)
    public VendorOrderDetailsResponseDto getVendorOrderDetails(Long vendorOrderId) {

        Vendor vendor = getLoggedInVendor();

        VendorOrder vendorOrder = vendorOrderRepository
                .findByIdAndVendor(vendorOrderId, vendor)
                .orElseThrow(() ->
                        new RuntimeException("Vendor order not found."));

        return VendorOrderMapper.toDetailsDto(vendorOrder);
    }

    // ===============================
// Vendor Dashboard
// ===============================

    @Transactional(readOnly = true)
    public BigDecimal getVendorRevenue() {

        Vendor vendor = getLoggedInVendor();

        return orderItemRepository.getVendorRevenue(vendor.getId());
    }


    @Transactional(readOnly = true)
    public long getVendorCustomers() {

        Vendor vendor = getLoggedInVendor();

        return orderItemRepository.countCustomers(vendor.getId());
    }

    @Transactional(readOnly = true)
    public Long getVendorProductsSold() {

        Vendor vendor = getLoggedInVendor();

        return orderItemRepository.getTotalProductsSold(vendor.getId());
    }


    // ===============================
// Update Vendor Order Status
// ===============================

    @Transactional
    public VendorOrderDetailsResponseDto updateVendorOrderStatus(
            Long vendorOrderId,
            UpdateVendorOrderStatusRequestDto request) {

        Vendor vendor = getLoggedInVendor();

        VendorOrder vendorOrder = vendorOrderRepository
                .findByIdAndVendor(vendorOrderId, vendor)
                .orElseThrow(() ->
                        new RuntimeException("Vendor order not found."));

        validateVendorOrderStatusTransition(
                vendorOrder.getOrderStatus(),
                request.orderStatus());

        vendorOrder.setOrderStatus(request.orderStatus());

        vendorOrderRepository.save(vendorOrder);

        return VendorOrderMapper.toDetailsDto(vendorOrder);
    }


    // ===============================
// Helper
// ===============================

    private Vendor getLoggedInVendor() {

        String userName = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return vendorRepository.findByUserUserName(userName)
                .orElseThrow(() ->
                        new RuntimeException("Vendor not found."));
    }


    private void validateVendorOrderStatusTransition(
            OrderStatus currentStatus,
            OrderStatus newStatus) {

        if (currentStatus == OrderStatus.DELIVERED) {
            throw new RuntimeException(
                    "Delivered vendor order cannot be updated.");
        }

        if (currentStatus == OrderStatus.CANCELLED) {
            throw new RuntimeException(
                    "Cancelled vendor order cannot be updated.");
        }

        if (currentStatus == newStatus) {
            throw new RuntimeException(
                    "Vendor order is already in " + newStatus + " status.");
        }

        switch (currentStatus) {

            case PENDING -> {
                if (newStatus != OrderStatus.CONFIRMED
                        && newStatus != OrderStatus.CANCELLED) {
                    throw new RuntimeException(
                            "Pending order can only be CONFIRMED or CANCELLED.");
                }
            }

            case CONFIRMED -> {
                if (newStatus != OrderStatus.PROCESSING
                        && newStatus != OrderStatus.CANCELLED) {
                    throw new RuntimeException(
                            "Confirmed order can only be PROCESSING or CANCELLED.");
                }
            }

            case PROCESSING -> {
                if (newStatus != OrderStatus.SHIPPED) {
                    throw new RuntimeException(
                            "Processing order can only be SHIPPED.");
                }
            }

            case SHIPPED -> {
                if (newStatus != OrderStatus.DELIVERED) {
                    throw new RuntimeException(
                            "Shipped order can only be DELIVERED.");
                }
            }
        }
    }

}
