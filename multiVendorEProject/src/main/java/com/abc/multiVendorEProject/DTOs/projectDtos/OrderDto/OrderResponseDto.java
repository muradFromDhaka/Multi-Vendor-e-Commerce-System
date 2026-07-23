package com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto;

import com.abc.multiVendorEProject.DTOs.projectDtos.OrderItemResponseDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.ShippingAddressResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.Vendor.Customer.CustomerVendorOrderDto;
import com.abc.multiVendorEProject.enums.OrderStatus;
import com.abc.multiVendorEProject.enums.PaymentMethod;
import com.abc.multiVendorEProject.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private Long id;

    private String orderNumber;

    private String userName;

    private BigDecimal subtotal;

    private BigDecimal shippingFee;

    private BigDecimal discount;

    private BigDecimal totalPrice;

    private OrderStatus orderStatus;

    private PaymentStatus paymentStatus;

    private PaymentMethod paymentMethod;

    private ShippingAddressResponseDto shippingAddress;

    private List<OrderItemResponseDTO> items;

    List<CustomerVendorOrderDto> vendorOrders;

    private LocalDateTime createdAt;
}

