package com.abc.multiVendorEProject.DTOs.projectDtos.Vendor.Customer;

import java.math.BigDecimal;

public record VendorCustomerOrderItemResponseDTO(

        Long productId,

        String productName,

        Long variantId,

        String sku,

        Integer quantity,

        BigDecimal unitPrice,

        BigDecimal totalPrice

) {
}