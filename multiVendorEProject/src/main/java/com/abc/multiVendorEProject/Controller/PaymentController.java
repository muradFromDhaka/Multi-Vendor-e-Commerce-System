package com.abc.multiVendorEProject.Controller;

import com.abc.multiVendorEProject.DTOs.projectDtos.PaymentDto.UpdatePaymentStatusRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.PaymentDto.PaymentRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.PaymentDto.PaymentResponseDto;
import com.abc.multiVendorEProject.enums.PaymentStatus;
import com.abc.multiVendorEProject.service.Customer.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping
    public ResponseEntity<PaymentResponseDto> createPayment(
            @Valid @RequestBody PaymentRequestDto dto){

        return ResponseEntity.ok(paymentService.createPayment(dto));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDto>  getPaymentById(@PathVariable Long paymentId){
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDto>  getPaymentByOrderId(@PathVariable Long orderId){
        return ResponseEntity.ok(paymentService.getPaymentByOrderId(orderId));
    }

}
