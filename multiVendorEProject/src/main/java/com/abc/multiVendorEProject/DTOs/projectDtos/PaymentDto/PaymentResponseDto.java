package com.abc.multiVendorEProject.DTOs.projectDtos.PaymentDto;


import com.abc.multiVendorEProject.enums.PaymentMethod;
import com.abc.multiVendorEProject.enums.PaymentProvider;
import com.abc.multiVendorEProject.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponseDto(
        Long orderId,
        Long paymentId,
        BigDecimal amount,
        LocalDateTime paidAt,
        String transactionId,
        PaymentProvider provider,
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus,
        String refundTransactionId,
        LocalDateTime refundedAt,
        BigDecimal refundedAmount
) {}