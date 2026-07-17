package com.abc.multiVendorEProject.DTOs.projectDtos.Vendor.Customer;

import com.abc.multiVendorEProject.enums.OrderStatus;
import com.abc.multiVendorEProject.enums.PaymentMethod;
import com.abc.multiVendorEProject.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record VendorCustomerOrderResponseDTO(

        Long orderId,

        String orderNumber,

        OrderStatus orderStatus,

        PaymentStatus paymentStatus,

        PaymentMethod paymentMethod,

        BigDecimal vendorTotalPrice,

        LocalDateTime orderDate,

        List<VendorCustomerOrderItemResponseDTO> items

) {
}
