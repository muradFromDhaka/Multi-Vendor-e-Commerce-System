package com.abc.multiVendorEProject.DTOs.projectDtos;


import com.abc.multiVendorEProject.enums.PaymentMethod;
import com.abc.multiVendorEProject.enums.PaymentProvider;
import com.abc.multiVendorEProject.enums.PaymentStatus;

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