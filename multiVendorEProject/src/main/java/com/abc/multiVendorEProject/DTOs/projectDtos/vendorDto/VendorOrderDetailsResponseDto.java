package com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto;


import com.abc.multiVendorEProject.DTOs.projectDtos.OrderItemResponseDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.ShippingAddressResponseDto;
import com.abc.multiVendorEProject.enums.OrderStatus;
import com.abc.multiVendorEProject.enums.PaymentMethod;
import com.abc.multiVendorEProject.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorOrderDetailsResponseDto {

    private Long id;

    private String orderNumber;

    private String customerName;

    private ShippingAddressResponseDto shippingAddress;

    private BigDecimal subtotal;

    private BigDecimal shippingFee;

    private BigDecimal discount;

    private BigDecimal totalPrice;

    private OrderStatus orderStatus;

    private PaymentStatus paymentStatus;

    private PaymentMethod paymentMethod;

    // Only Logged-in Vendor's Items
    private List<OrderItemResponseDTO> items;

    private String createdAt;
}