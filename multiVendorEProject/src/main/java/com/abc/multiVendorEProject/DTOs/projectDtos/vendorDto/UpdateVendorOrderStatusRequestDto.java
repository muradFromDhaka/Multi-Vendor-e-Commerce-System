package com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto;

import com.abc.multiVendorEProject.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateVendorOrderStatusRequestDto(

        @NotNull(message = "Order status is required")
        OrderStatus orderStatus

) {
}