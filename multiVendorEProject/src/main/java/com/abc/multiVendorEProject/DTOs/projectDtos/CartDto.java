package com.abc.multiVendorEProject.DTOs.projectDtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDto {

    private Long cartId;
    private List<CartItemResponseDTO> items;
    private BigDecimal totalAmount;


}
