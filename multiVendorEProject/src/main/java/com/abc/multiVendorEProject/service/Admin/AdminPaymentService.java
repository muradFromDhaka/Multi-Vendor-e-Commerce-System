package com.abc.multiVendorEProject.service.Admin;

import com.abc.multiVendorEProject.DTOs.projectDtos.PaymentDto.PaymentResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.PaymentDto.UpdatePaymentStatusRequestDto;
import com.abc.multiVendorEProject.entity.Payment;
import com.abc.multiVendorEProject.enums.PaymentMethod;
import com.abc.multiVendorEProject.enums.PaymentStatus;
import com.abc.multiVendorEProject.mapper.PaymentMapper;
import com.abc.multiVendorEProject.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminPaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;


//    ==========================Helper method===============================

    private Payment getPaymentOrThrow(Long paymentId) {

        return paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found."));
    }

    private void validateStatusTransition(
            PaymentStatus currentStatus,
            PaymentStatus newStatus) {

        if (currentStatus == newStatus) {
            throw new RuntimeException(
                    "Payment is already " + newStatus);
        }

        switch (currentStatus) {

            case PENDING -> {

                if (newStatus != PaymentStatus.PAID
                        && newStatus != PaymentStatus.FAILED
                        && newStatus != PaymentStatus.CANCELLED) {

                    throw new RuntimeException(
                            "Pending payment can only become PAID, FAILED or CANCELLED.");
                }
            }

            case FAILED -> {

                if (newStatus != PaymentStatus.PAID
                        && newStatus != PaymentStatus.CANCELLED) {

                    throw new RuntimeException(
                            "Failed payment can only become PAID or CANCELLED.");
                }
            }

            case PAID -> {

                if (newStatus != PaymentStatus.REFUNDED) {

                    throw new RuntimeException(
                            "Paid payment can only become REFUNDED.");
                }
            }

            case REFUNDED ->
                    throw new RuntimeException(
                            "Refunded payment cannot be updated.");

            case CANCELLED ->
                    throw new RuntimeException(
                            "Cancelled payment cannot be updated.");
        }
    }


    private void validateRequest(
            Payment payment,
            UpdatePaymentStatusRequestDto request) {

        switch (request.paymentStatus()) {

            case PAID -> {

                if (payment.getPaymentMethod() != PaymentMethod.CASH_ON_DELIVERY) {

                    if (request.transactionId() == null
                            || request.transactionId().isBlank()) {

                        throw new RuntimeException(
                                "Transaction Id is required.");
                    }

                    Payment existing =
                            paymentRepository.findByTransactionId(
                                            request.transactionId())
                                    .orElse(null);

                    if (existing != null
                            && !existing.getId().equals(payment.getId())) {

                        throw new RuntimeException(
                                "Transaction Id already exists.");
                    }
                }
            }

            case REFUNDED -> {

                if (payment.getPaymentMethod()
                        == PaymentMethod.CASH_ON_DELIVERY) {

                    throw new RuntimeException(
                            "COD payment cannot be refunded.");
                }

                if (request.refundTransactionId() == null
                        || request.refundTransactionId().isBlank()) {

                    throw new RuntimeException(
                            "Refund Transaction Id required.");
                }

                if (request.refundedAmount() == null
                        || request.refundedAmount()
                        .compareTo(BigDecimal.ZERO) <= 0) {

                    throw new RuntimeException(
                            "Refund amount must be greater than zero.");
                }

                if (request.refundedAmount()
                        .compareTo(payment.getAmount()) > 0) {

                    throw new RuntimeException(
                            "Refund amount exceeds payment amount.");
                }
            }

            default -> {
            }
        }
    }


    private void updateAuditFields(
            Payment payment,
            UpdatePaymentStatusRequestDto request) {

        switch (request.paymentStatus()) {

            case PAID -> {

                payment.setPaidAt(LocalDateTime.now());
                payment.setTransactionId(request.transactionId());

                payment.setRefundedAt(null);
                payment.setRefundTransactionId(null);
                payment.setRefundedAmount(null);
            }

            case REFUNDED -> {

                payment.setRefundedAt(LocalDateTime.now());
                payment.setRefundTransactionId(
                        request.refundTransactionId());

                payment.setRefundedAmount(
                        request.refundedAmount());
            }

            case FAILED,
                 CANCELLED -> {

                payment.setPaidAt(null);
                payment.setTransactionId(null);

                payment.setRefundedAt(null);
                payment.setRefundTransactionId(null);
                payment.setRefundedAmount(null);
            }

            case PENDING -> {
            }
        }
    }


//    =============================Original Method=============================

    @Transactional
    public PaymentResponseDto updatePaymentStatus(
            Long paymentId,
            UpdatePaymentStatusRequestDto request) {

        Payment payment = getPaymentOrThrow(paymentId);

        validateStatusTransition(
                payment.getPaymentStatus(),
                request.paymentStatus());

        validateRequest(payment, request);

        payment.setPaymentStatus(
                request.paymentStatus());

        updateAuditFields(payment, request);

        return paymentMapper.toDto(
                paymentRepository.save(payment));
    }

    @Transactional(readOnly = true)
    public PaymentResponseDto getPaymentById(Long paymentId) {

        Payment payment = getPaymentOrThrow(paymentId);

        return paymentMapper.toDto(payment);
    }


    @Transactional(readOnly = true)
    public PaymentResponseDto getPaymentByOrderId(Long orderId) {

        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found."));

        return paymentMapper.toDto(payment);
    }


    @Transactional(readOnly = true)
    public Page<PaymentResponseDto> getAllPayments(Pageable pageable) {

        return paymentRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(paymentMapper::toDto);
    }


    @Transactional(readOnly = true)
    public Page<PaymentResponseDto> getPaymentsByMethod(
            PaymentMethod method,
            Pageable pageable) {

        return paymentRepository
                .findByPaymentMethod(method, pageable)
                .map(paymentMapper::toDto);
    }


    @Transactional(readOnly = true)
    public PaymentResponseDto getByTransactionId(
            String transactionId) {

        Payment payment = paymentRepository
                .findByTransactionId(transactionId)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found."));

        return paymentMapper.toDto(payment);
    }


    @Transactional(readOnly = true)
    public PaymentResponseDto getPaymentByOrderNumber(String orderNumber) {

        Payment payment = paymentRepository
                .findByOrderOrderNumber(orderNumber)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found."));

        return paymentMapper.toDto(payment);
    }

    @Transactional(readOnly = true)
    public PaymentResponseDto getPaymentByRefundTransactionId(
            String refundTransactionId) {

        Payment payment = paymentRepository
                .findByRefundTransactionId(refundTransactionId)
                .orElseThrow(() ->
                        new RuntimeException("Refund payment not found."));

        return paymentMapper.toDto(payment);
    }


    @Transactional(readOnly = true)
    public Page<PaymentResponseDto> getPaymentsByStatus(
            PaymentStatus status,
            Pageable pageable) {

        return paymentRepository
                .findByPaymentStatusOrderByPaidAtDesc(
                        status,
                        pageable)
                .map(paymentMapper::toDto);
    }


    @Transactional(readOnly = true)
    public Page<PaymentResponseDto> getPayments(
            PaymentStatus paymentStatus,
            PaymentMethod paymentMethod,
            Pageable pageable) {

        return paymentRepository
                .findByPaymentStatusAndPaymentMethod(
                        paymentStatus,
                        paymentMethod,
                        pageable)
                .map(paymentMapper::toDto);
    }


    @Transactional(readOnly = true)
    public Page<PaymentResponseDto> searchByCustomer(
            String username,
            Pageable pageable) {

        return paymentRepository
                .findByOrderUserUserNameContainingIgnoreCase(
                        username,
                        pageable)
                .map(paymentMapper::toDto);
    }


}
