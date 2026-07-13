package com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto;

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
public class AdminOrderListResponseDto {

    private Long id;

    private String orderNumber;

    private String customerName;

    private Integer totalItems;

    private BigDecimal totalPrice;

    private OrderStatus orderStatus;

    private PaymentStatus paymentStatus;

    private String createdAt;
}
