package com.abc.multiVendorEProject.mapper;

import com.abc.multiVendorEProject.DTOs.projectDtos.PaymentRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.PaymentResponseDto;
import com.abc.multiVendorEProject.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment toEntity(PaymentRequestDto dto){
        Payment payment = new Payment();

        payment.setProvider(dto.provider());
        payment.setTransactionId(dto.transactionId());
        payment.setPaymentMethod(dto.paymentMethod());

        return payment;
    }


    public PaymentResponseDto toDto(Payment payment){
        PaymentResponseDto dto = new PaymentResponseDto(
                payment.getId(),
                payment.getOrder().getId(),
                payment.getAmount(),
                payment.getProvider(),
                payment.getTransactionId(),
                payment.getPaymentMethod(),
                payment.getPaidAt(),
                payment.getStatus()
        );

        return dto;
    }


    public void updateEntity(Payment payment, PaymentRequestDto dto){

        payment.setProvider(dto.provider());
        payment.setTransactionId(dto.transactionId());
        payment.setPaymentMethod(dto.paymentMethod());

    }
}
