package com.abc.multiVendorEProject.DTOs.projectDtos.Vendor.Customer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record VendorCustomerResponseDTO(
        String userName,
        String fullName,
        String email,
        String phone,
        Long totalOrders,
        BigDecimal totalSpent,
        LocalDateTime lastOrderDate

) {}
