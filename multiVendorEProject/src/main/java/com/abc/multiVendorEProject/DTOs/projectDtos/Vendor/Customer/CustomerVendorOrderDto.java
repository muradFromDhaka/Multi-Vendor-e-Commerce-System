package com.abc.multiVendorEProject.DTOs.projectDtos.Vendor.Customer;

import com.abc.multiVendorEProject.DTOs.projectDtos.OrderItemResponseDTO;
import com.abc.multiVendorEProject.enums.VendorOrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record CustomerVendorOrderDto(
        Long vendorOrderId,
        Long vendorId,
        String vendorName,
        VendorOrderStatus vendorOrderStatus,
        List<OrderItemResponseDTO> items,
        LocalDateTime updatedAt
) {}