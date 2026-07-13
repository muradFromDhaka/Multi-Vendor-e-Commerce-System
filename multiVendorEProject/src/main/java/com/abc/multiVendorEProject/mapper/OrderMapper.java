package com.abc.multiVendorEProject.mapper;

import com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto.AdminOrderDetailsResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto.AdminOrderListResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto.OrderResponseDto;
import com.abc.multiVendorEProject.entity.Order;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderMapper {

    private OrderMapper() {}
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static OrderResponseDto toResponseDto(Order order) {

        return new OrderResponseDto(
                order.getId(),
                order.getOrderNumber(),
                order.getUser().getUserName(),
                order.getSubtotal(),
                order.getShippingFee(),
                order.getDiscount(),
                order.getTotalPrice(),
                order.getOrderStatus(),
                order.getPaymentStatus(),
                order.getPaymentMethod(),
                ShippingAddressMapper.toResponseDto(order.getShippingAddress()),
                order.getOrderItems()
                        .stream()
                        .map(OrderItemMapper::toResponseDto)
                        .toList(),
                order.getCreatedAt().format(FORMATTER)
        );
    }

    public static AdminOrderListResponseDto toAdminListDto(Order order) {

        return new AdminOrderListResponseDto(
                order.getId(),
                order.getOrderNumber(),
                order.getUser().getUserName(),
                order.getOrderItems().size(),
                order.getTotalPrice(),
                order.getOrderStatus(),
                order.getPaymentStatus(),
                order.getCreatedAt().format(FORMATTER)
        );
    }

    public static AdminOrderDetailsResponseDto toAdminDetailsDto(Order order) {

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
                order.getPaymentStatus(),
                order.getPaymentMethod(),
                ShippingAddressMapper.toResponseDto(order.getShippingAddress()),
                order.getOrderItems()
                        .stream()
                        .map(OrderItemMapper::toResponseDto)
                        .toList(),
                order.getCreatedAt().format(FORMATTER),
                order.getUpdatedAt().format(FORMATTER)
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