package com.abc.multiVendorEProject.DTOs.projectDtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponseDTO {
    private Long cartItemId;
    private Long productId;
    private Long productVariantId;

    private String productName;
    private String sku;

    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice; // Total price for this item (quantity * price)
    private String imageUrl;

    private Integer vendorId;
    private String vendorName;
}

