package com.abc.multiVendorEProject.Controller.Admin;

import com.abc.multiVendorEProject.DTOs.projectDtos.PaymentDto.PaymentResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.PaymentDto.UpdatePaymentStatusRequestDto;
import com.abc.multiVendorEProject.enums.PaymentMethod;
import com.abc.multiVendorEProject.enums.PaymentStatus;
import com.abc.multiVendorEProject.service.Admin.AdminPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/payments")
public class AdminPaymentController {

    private final AdminPaymentService adminPaymentService;

    // =====================================================
    // Update Payment Status
    // =====================================================

    @PutMapping("/{paymentId}/status")
    public ResponseEntity<PaymentResponseDto> updatePaymentStatus(
            @PathVariable Long paymentId,
            @RequestBody UpdatePaymentStatusRequestDto request) {

        return ResponseEntity.ok(
                adminPaymentService.updatePaymentStatus(
                        paymentId,
                        request));
    }

    // =====================================================
    // Get Payment By Id
    // =====================================================

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDto> getPaymentById(
            @PathVariable Long paymentId) {

        return ResponseEntity.ok(
                adminPaymentService.getPaymentById(paymentId));
    }

    // =====================================================
    // Get Payment By Order Id
    // =====================================================

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDto> getPaymentByOrderId(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                adminPaymentService.getPaymentByOrderId(orderId));
    }

    // =====================================================
    // Get Payment By Order Number
    // =====================================================

    @GetMapping("/order-number/{orderNumber}")
    public ResponseEntity<PaymentResponseDto> getPaymentByOrderNumber(
            @PathVariable String orderNumber) {

        return ResponseEntity.ok(
                adminPaymentService.getPaymentByOrderNumber(orderNumber));
    }

    // =====================================================
    // Get Payment By Transaction Id
    // =====================================================

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<PaymentResponseDto> getPaymentByTransactionId(
            @PathVariable String transactionId) {

        return ResponseEntity.ok(
                adminPaymentService.getByTransactionId(transactionId));
    }

    // =====================================================
    // Get Payment By Refund Transaction Id
    // =====================================================

    @GetMapping("/refund/{refundTransactionId}")
    public ResponseEntity<PaymentResponseDto> getRefundPayment(
            @PathVariable String refundTransactionId) {

        return ResponseEntity.ok(
                adminPaymentService.getPaymentByRefundTransactionId(
                        refundTransactionId));
    }

    // =====================================================
    // Get All Payments
    // =====================================================

    @GetMapping
    public ResponseEntity<Page<PaymentResponseDto>> getAllPayments(
            Pageable pageable) {

        return ResponseEntity.ok(
                adminPaymentService.getAllPayments(pageable));
    }

    // =====================================================
    // Get Payments By Status
    // =====================================================

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<PaymentResponseDto>> getPaymentsByStatus(
            @PathVariable PaymentStatus status,
            Pageable pageable) {

        return ResponseEntity.ok(
                adminPaymentService.getPaymentsByStatus(
                        status,
                        pageable));
    }

    // =====================================================
    // Get Payments By Method
    // =====================================================

    @GetMapping("/method/{method}")
    public ResponseEntity<Page<PaymentResponseDto>> getPaymentsByMethod(
            @PathVariable PaymentMethod method,
            Pageable pageable) {

        return ResponseEntity.ok(
                adminPaymentService.getPaymentsByMethod(
                        method,
                        pageable));
    }

    // =====================================================
    // Filter By Status & Method
    // =====================================================

    @GetMapping("/filter")
    public ResponseEntity<Page<PaymentResponseDto>> getPayments(
            @RequestParam PaymentStatus paymentStatus,
            @RequestParam PaymentMethod paymentMethod,
            Pageable pageable) {

        return ResponseEntity.ok(
                adminPaymentService.getPayments(
                        paymentStatus,
                        paymentMethod,
                        pageable));
    }

    // =====================================================
    // Search Customer
    // =====================================================

    @GetMapping("/search")
    public ResponseEntity<Page<PaymentResponseDto>> searchCustomerPayments(
            @RequestParam String username,
            Pageable pageable) {

        return ResponseEntity.ok(
                adminPaymentService.searchByCustomer(
                        username,
                        pageable));
    }

}