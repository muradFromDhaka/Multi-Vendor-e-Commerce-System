package com.abc.multiVendorEProject.mapper;

import com.abc.multiVendorEProject.DTOs.projectDtos.OrderItemResponseDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorOrderDetailsResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorOrderListResponseDto;
import com.abc.multiVendorEProject.entity.VendorOrder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VendorOrderMapper {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public static VendorOrderListResponseDto toListDto(
            VendorOrder vendorOrder) {

        return new VendorOrderListResponseDto(
                vendorOrder.getId(),
                vendorOrder.getVendorOrderNumber(),
                vendorOrder.getOrder()
                        .getShippingAddress()
                        .getUser().getUserName(),
                vendorOrder.getOrderItems().size(),
                vendorOrder.getTotalPrice(),
                vendorOrder.getOrderStatus(),
                vendorOrder.getPaymentStatus(),
                vendorOrder.getCreatedAt().format(FORMATTER)
        );
    }


    public static VendorOrderDetailsResponseDto toDetailsDto(
            VendorOrder vendorOrder) {

        List<OrderItemResponseDTO> items = vendorOrder.getOrderItems()
                .stream()
                .map(OrderItemMapper::toResponseDto)
                .toList();

        return new VendorOrderDetailsResponseDto(
                vendorOrder.getId(),
                vendorOrder.getVendorOrderNumber(),
                vendorOrder.getOrder()
                        .getShippingAddress()
                        .getUser().getUserName(),
                ShippingAddressMapper.toResponseDto(
                        vendorOrder.getOrder().getShippingAddress()
                ),
                vendorOrder.getSubtotal(),
                vendorOrder.getShippingFee(),
                vendorOrder.getDiscount(),
                vendorOrder.getTotalPrice(),
                vendorOrder.getOrderStatus(),
                vendorOrder.getPaymentStatus(),
                vendorOrder.getPaymentMethod(),
                items,
                vendorOrder.getCreatedAt().format(FORMATTER)
        );
    }


    public static List<VendorOrderListResponseDto> toListDtoList(
            List<VendorOrder> vendorOrders) {

        return vendorOrders.stream()
                .map(VendorOrderMapper::toListDto)
                .toList();
    }
}