package com.abc.multiVendorEProject.DTOs.projectDtos.vendorOrderDto;

import com.abc.multiVendorEProject.enums.VendorOrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateVendorOrderStatusRequestDto(

        @NotNull(message = "vendorOrder status is required")
        VendorOrderStatus vendorOrderStatus

) {
}