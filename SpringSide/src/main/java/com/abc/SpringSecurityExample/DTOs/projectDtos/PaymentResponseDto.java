package com.abc.SpringSecurityExample.DTOs.projectDtos;

import com.abc.SpringSecurityExample.enums.PaymentMethod;
import com.abc.SpringSecurityExample.enums.PaymentProvider;
import com.abc.SpringSecurityExample.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponseDto(
        Long paymentId,
        Long orderId,
        BigDecimal amount,
        PaymentProvider provider,
        String transactionId,
        PaymentMethod paymentMethod,
        LocalDateTime paidAt,
        PaymentStatus status
) {}