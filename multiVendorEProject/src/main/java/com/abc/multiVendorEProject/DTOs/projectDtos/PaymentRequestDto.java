package com.abc.multiVendorEProject.DTOs.projectDtos;


import com.abc.multiVendorEProject.enums.PaymentMethod;
import com.abc.multiVendorEProject.enums.PaymentProvider;

public record PaymentRequestDto(
        Long orderId,
        PaymentProvider provider,
        String transactionId,
        PaymentMethod paymentMethod
 ) {}