package com.abc.multiVendorEProject.service.Customer;

import com.abc.multiVendorEProject.DTOs.projectDtos.PaymentDto.UpdatePaymentStatusRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.PaymentDto.PaymentRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.PaymentDto.PaymentResponseDto;
import com.abc.multiVendorEProject.Util.NotFoundException;
import com.abc.multiVendorEProject.entity.Order;
import com.abc.multiVendorEProject.entity.Payment;
import com.abc.multiVendorEProject.enums.PaymentMethod;
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

import java.math.BigDecimal;
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

        if (order.getPayment() != null) {
            throw new RuntimeException("Payment already exists.");
        }

        // 3. Prevent duplicate transaction ID
        if (dto.transactionId()!=null && paymentRepository.existsByTransactionId(dto.transactionId())) {
            throw new RuntimeException("Transaction ID already exists");
        }

        // 4. Map DTO → Entity
        Payment payment = paymentMapper.toEntity(dto);

        // 5. Set system-controlled fields
        payment.setOrder(order);
        payment.setAmount(order.getTotalPrice());
        payment.setPaidAt(null);
        payment.setPaymentStatus(PaymentStatus.PENDING);

        Payment savedPayment = paymentRepository.save(payment);

        return paymentMapper.toDto(savedPayment);
    }


    public PaymentResponseDto getPaymentById(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));

        return paymentMapper.toDto(payment);
    }



    public PaymentResponseDto getPaymentByOrderId(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found with id: " + orderId));

        Payment payment = paymentRepository.findByOrder(order)
                .orElseThrow(() -> new NotFoundException("Payment not found for this order"));

        return paymentMapper.toDto(payment);
    }
}