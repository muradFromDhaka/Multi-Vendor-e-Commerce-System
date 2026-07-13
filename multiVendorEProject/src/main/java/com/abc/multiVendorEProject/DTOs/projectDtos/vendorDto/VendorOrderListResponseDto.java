package com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto;


import com.abc.multiVendorEProject.enums.OrderStatus;
import com.abc.multiVendorEProject.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorOrderListResponseDto {

    private Long id;

    private String orderNumber;

    private String customerName;

    private Integer totalItems;

    private BigDecimal orderAmount;

    private OrderStatus orderStatus;

    private PaymentStatus paymentStatus;

    private String createdAt;
}