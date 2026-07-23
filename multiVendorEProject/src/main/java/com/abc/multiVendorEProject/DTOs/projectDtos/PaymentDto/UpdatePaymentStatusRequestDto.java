package com.abc.multiVendorEProject.DTOs.projectDtos.PaymentDto;

import com.abc.multiVendorEProject.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdatePaymentStatusRequestDto(

        @NotNull
        PaymentStatus paymentStatus,

        String transactionId,

        String refundTransactionId,

        BigDecimal refundedAmount
) {}
