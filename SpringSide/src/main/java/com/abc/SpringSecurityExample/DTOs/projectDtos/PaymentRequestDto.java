package com.abc.SpringSecurityExample.DTOs.projectDtos;

import com.abc.SpringSecurityExample.enums.PaymentMethod;
import com.abc.SpringSecurityExample.enums.PaymentProvider;

public record PaymentRequestDto(
        Long orderId,
        PaymentProvider provider,
        String transactionId,
        PaymentMethod paymentMethod
 ) {}