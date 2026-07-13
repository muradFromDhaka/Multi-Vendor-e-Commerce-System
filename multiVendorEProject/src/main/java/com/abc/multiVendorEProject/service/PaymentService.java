package com.abc.multiVendorEProject.service;

import com.abc.multiVendorEProject.DTOs.projectDtos.PaymentRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.PaymentResponseDto;
import com.abc.multiVendorEProject.entity.Order;
import com.abc.multiVendorEProject.entity.Payment;
import com.abc.multiVendorEProject.enums.PaymentStatus;
import com.abc.multiVendorEProject.mapper.PaymentMapper;
import com.abc.multiVendorEProject.repository.OrderRepository;
import com.abc.multiVendorEProject.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMapper paymentMapper;

    @Transactional
    public PaymentResponseDto createPayment(PaymentRequestDto dto) {

        // 1. Validate Order
        Order order = orderRepository.findById(dto.orderId())
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + dto.orderId()));

        // 2. Prevent duplicate payment for same order
        paymentRepository.findByOrder(order).ifPresent(p -> {
            throw new RuntimeException("Payment already exists for this order");
        });

        // 3. Prevent duplicate transaction ID
        if (paymentRepository.existsByTransactionId(dto.transactionId())) {
            throw new RuntimeException("Transaction ID already exists");
        }

        // 4. Map DTO → Entity
        Payment payment = paymentMapper.toEntity(dto);

        // 5. Set system-controlled fields
        payment.setOrder(order);
        payment.setAmount(order.getTotalPrice());
        payment.setPaidAt(LocalDateTime.now());
        payment.setStatus(PaymentStatus.PENDING);

        Payment savedPayment = paymentRepository.save(payment);

        return paymentMapper.toDto(savedPayment);
    }


    @Transactional
    public PaymentResponseDto updatePayment(Long paymentId, PaymentRequestDto dto) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentId));

        paymentMapper.updateEntity(payment, dto);

        Payment updated = paymentRepository.save(payment);

        return paymentMapper.toDto(updated);
    }



    @Transactional
    public PaymentResponseDto updatePaymentStatus(Long paymentId, PaymentStatus status) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentId));

        payment.setStatus(status);

        if (status == PaymentStatus.PAID) {
            payment.setPaidAt(LocalDateTime.now());
        }

        Payment updated = paymentRepository.save(payment);

        return paymentMapper.toDto(updated);
    }




    public Page<PaymentResponseDto> getAllPayments(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return paymentRepository.findAll(pageable)
                .map(paymentMapper::toDto);
    }



    public Page<PaymentResponseDto> getPaymentsByStatus(
            PaymentStatus status,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return paymentRepository.findByStatus(status, pageable)
                .map(paymentMapper::toDto);
    }


    public PaymentResponseDto getPaymentById(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));

        return paymentMapper.toDto(payment);
    }



    public PaymentResponseDto getPaymentByOrderId(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        Payment payment = paymentRepository.findByOrder(order)
                .orElseThrow(() -> new RuntimeException("Payment not found for this order"));

        return paymentMapper.toDto(payment);
    }


    public void deletePayment(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));

        paymentRepository.delete(payment);
    }
}