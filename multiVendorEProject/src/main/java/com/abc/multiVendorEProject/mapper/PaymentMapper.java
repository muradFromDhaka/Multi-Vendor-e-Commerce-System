package com.abc.multiVendorEProject.mapper;

import com.abc.multiVendorEProject.DTOs.projectDtos.PaymentDto.PaymentRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.PaymentDto.PaymentResponseDto;
import com.abc.multiVendorEProject.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment toEntity(PaymentRequestDto dto) {

        Payment payment = new Payment();

        payment.setProvider(dto.provider());
        payment.setTransactionId(dto.transactionId());
        payment.setPaymentMethod(dto.paymentMethod());

        return payment;
    }

    public PaymentResponseDto toDto(Payment payment) {

        return new PaymentResponseDto(
                payment.getOrder().getId(),          // orderId
                payment.getId(),                     // paymentId
                payment.getAmount(),                 // amount
                payment.getPaidAt(),                 // paidAt
                payment.getTransactionId(),          // transactionId
                payment.getProvider(),               // provider
                payment.getPaymentMethod(),          // paymentMethod
                payment.getPaymentStatus(),          // paymentStatus
                payment.getRefundTransactionId(),
                payment.getRefundedAt(),
                payment.getRefundedAmount()
        );
    }

    public void updateEntity(Payment payment, PaymentRequestDto dto) {

        payment.setProvider(dto.provider());
        payment.setTransactionId(dto.transactionId());
        payment.setPaymentMethod(dto.paymentMethod());
    }
}