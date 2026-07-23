package com.abc.multiVendorEProject.DTOs.projectDtos.vendorOrderDto;


import com.abc.multiVendorEProject.enums.PaymentStatus;
import com.abc.multiVendorEProject.enums.VendorOrderStatus;
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
public class VendorOrderListResponseDto {

    private Long id;

    private String orderNumber;

    private String customerName;

    private Integer totalItems;

    private BigDecimal vendorOrderAmount;

    private VendorOrderStatus vendorOrderStatus;

    private PaymentStatus paymentStatus;

    private LocalDateTime createdAt;
}