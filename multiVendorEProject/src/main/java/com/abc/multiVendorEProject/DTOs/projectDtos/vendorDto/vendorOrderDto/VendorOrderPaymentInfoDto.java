package com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.vendorOrderDto;

import com.abc.multiVendorEProject.enums.PaymentMethod;
import com.abc.multiVendorEProject.enums.PaymentStatus;

import java.time.LocalDateTime;

public record VendorOrderPaymentInfoDto(
        PaymentStatus paymentStatus,
        PaymentMethod paymentMethod,
        String transactionId,
        LocalDateTime paidAt
) {}
