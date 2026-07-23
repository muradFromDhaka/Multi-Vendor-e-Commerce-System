package com.abc.multiVendorEProject.mapper;

import com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto.AdminOrderDetailsResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto.AdminOrderListResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto.OrderResponseDto;
import com.abc.multiVendorEProject.entity.Order;
import com.abc.multiVendorEProject.entity.Payment;
import com.abc.multiVendorEProject.mapper.Customer.CustomerVendorOrderMapper;
import java.util.List;

public class OrderMapper {

    private OrderMapper() {}

    public static OrderResponseDto toResponseDto(Order order) {

        Payment payment = order.getPayment();

        return new OrderResponseDto(
                order.getId(),
                order.getOrderNumber(),
                order.getUser().getUserName(),
                order.getSubtotal(),
                order.getShippingFee(),
                order.getDiscount(),
                order.getTotalPrice(),
                order.getOrderStatus(),
                payment != null ? payment.getPaymentStatus() : null,
                payment != null ? payment.getPaymentMethod() : null,
                ShippingAddressMapper.toResponseDto(order.getShippingAddress()),
                order.getOrderItems()
                        .stream()
                        .map(OrderItemMapper::toResponseDto)
                        .toList(),
                order.getVendorOrders()
                        .stream()
                        .map(CustomerVendorOrderMapper::toCustomerDto)
                        .toList(),
                order.getCreatedAt()
        );
    }

    public static AdminOrderListResponseDto toAdminListDto(Order order) {

        Payment payment = order.getPayment();

        return new AdminOrderListResponseDto(
                order.getId(),
                order.getOrderNumber(),
                order.getUser().getUserName(),
                order.getOrderItems().size(),
                order.getTotalPrice(),
                order.getOrderStatus(),
                payment != null ? payment.getPaymentStatus() : null,
                order.getCreatedAt()
        );
    }

    public static AdminOrderDetailsResponseDto toAdminDetailsDto(Order order) {

        Payment payment = order.getPayment();

        return new AdminOrderDetailsResponseDto(
                order.getId(),
                order.getOrderNumber(),
                order.getUser().getUserName(),
                order.getUser().getEmail(),
                order.getShippingAddress().getPhone(),
                order.getSubtotal(),
                order.getShippingFee(),
                order.getDiscount(),
                order.getTotalPrice(),
                order.getOrderStatus(),
                payment != null ? payment.getPaymentStatus() : null,
                payment != null ? payment.getPaymentMethod() : null,
                ShippingAddressMapper.toResponseDto(order.getShippingAddress()),
                order.getOrderItems()
                        .stream()
                        .map(OrderItemMapper::toResponseDto)
                        .toList(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }

    public static List<OrderResponseDto> toResponseDtoList(List<Order> orders) {
        return orders.stream()
                .map(OrderMapper::toResponseDto)
                .toList();
    }

    public static List<AdminOrderListResponseDto> toAdminListDtoList(List<Order> orders) {
        return orders.stream()
                .map(OrderMapper::toAdminListDto)
                .toList();
    }
}