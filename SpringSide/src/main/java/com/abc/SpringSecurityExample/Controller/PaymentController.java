package com.abc.SpringSecurityExample.Controller;

import com.abc.SpringSecurityExample.DTOs.projectDtos.PaymentRequestDto;
import com.abc.SpringSecurityExample.DTOs.projectDtos.PaymentResponseDto;
import com.abc.SpringSecurityExample.enums.PaymentStatus;
import com.abc.SpringSecurityExample.service.PaymentService;
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
    public ResponseEntity<PaymentResponseDto> createPayment(@RequestBody PaymentRequestDto dto){
        return ResponseEntity.ok(paymentService.createPayment(dto));
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDto> updatePayment(
            @PathVariable Long paymentId,
            @RequestBody PaymentRequestDto dto){

        return ResponseEntity.ok(paymentService.updatePayment(paymentId,dto));
    }

    @PutMapping("/{paymentId}/status")
    public ResponseEntity<PaymentResponseDto> updatePaymentStatus(
            @PathVariable Long paymentId,
            @RequestParam PaymentStatus status){

        return ResponseEntity.ok(paymentService.updatePaymentStatus(paymentId, status));
    }


    @GetMapping
    public Page<PaymentResponseDto> getAllPayments(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam String sortDir){

        return paymentService.getAllPayments(page,size,sortBy,sortDir);
    }

    @GetMapping("/status")
    public Page<PaymentResponseDto> getPaymentsByStatus(
            @RequestParam PaymentStatus status,
            @RequestParam int page,
            @RequestParam int size){

        return paymentService.getPaymentsByStatus(status,page,size);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDto>  getPaymentById(@PathVariable Long paymentId){
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDto>  getPaymentByOrderId(@PathVariable Long orderId){
        return ResponseEntity.ok(paymentService.getPaymentByOrderId(orderId));
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<?> deletePayment(@PathVariable Long paymentId){
        paymentService.deletePayment(paymentId);

        return ResponseEntity.noContent().build();
    }
}
