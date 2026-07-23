package com.abc.multiVendorEProject.DTOs.projectDtos.vendorOrderDto;


import com.abc.multiVendorEProject.DTOs.projectDtos.OrderItemResponseDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.PaymentDto.PaymentResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.ShippingAddressResponseDto;
import com.abc.multiVendorEProject.enums.PaymentMethod;
import com.abc.multiVendorEProject.enums.PaymentStatus;
import com.abc.multiVendorEProject.enums.VendorOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorOrderDetailsResponseDto {

    private Long id;

    private String orderNumber;

    private String vendorName;

    private String customerName;

    private ShippingAddressResponseDto shippingAddress;

    private BigDecimal subtotal;

    private BigDecimal shippingFee;

    private BigDecimal discount;

    private BigDecimal totalPrice;

    private VendorOrderStatus vendorOrderStatus;

    private VendorOrderPaymentInfoDto payment;

    // Only Logged-in Vendor's Items
    private List<OrderItemResponseDTO> items;

    private LocalDateTime createdAt;
}