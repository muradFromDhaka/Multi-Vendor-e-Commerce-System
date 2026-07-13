package com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto;

import com.abc.multiVendorEProject.DTOs.projectDtos.OrderItemRequestDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.ShippingAddressRequestDto;
import com.abc.multiVendorEProject.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {

    @NotNull
    private ShippingAddressRequestDto shippingAddress;

    @NotNull
    private PaymentMethod paymentMethod;

    @NotNull
    private List<OrderItemRequestDTO> items;
}
