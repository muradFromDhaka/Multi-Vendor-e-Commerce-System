package com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.Inventory;

import com.abc.multiVendorEProject.enums.StockStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryListResponseDto {

    private Long variantId;

    private String productName;

    private String sku;

    private String imageUrl;

    private String attributes;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private Integer stock;

    private StockStatus stockStatus;

    private LocalDateTime updatedAt;
}
