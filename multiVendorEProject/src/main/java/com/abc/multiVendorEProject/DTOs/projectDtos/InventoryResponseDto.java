package com.abc.multiVendorEProject.DTOs.projectDtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InventoryResponseDto {
    private Long id; // 🔥 important
    private String inventoryName;
    private Integer availableQuantity;
    private Integer reservedQuantity;
    private Long productId;
}
