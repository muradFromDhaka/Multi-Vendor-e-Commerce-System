package com.abc.multiVendorEProject.mapper;

import com.abc.multiVendorEProject.DTOs.projectDtos.OrderItemResponseDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorOrderDto.VendorOrderDetailsResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorOrderDto.VendorOrderListResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorOrderDto.VendorOrderPaymentInfoDto;
import com.abc.multiVendorEProject.entity.VendorOrder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

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
                vendorOrder.getVendorOrderStatus(),
                vendorOrder.getOrder().getPayment().getPaymentStatus(),
                vendorOrder.getCreatedAt()
        );
    }


    public static VendorOrderDetailsResponseDto toDetailsDto(
            VendorOrder vendorOrder) {

        List<OrderItemResponseDTO> items = vendorOrder.getOrderItems()
                .stream()
                .map(OrderItemMapper::toResponseDto)
                .toList();

        VendorOrderPaymentInfoDto paymentInfoDto = new VendorOrderPaymentInfoDto(
                vendorOrder.getOrder().getPayment().getPaymentStatus(),
                vendorOrder.getOrder().getPayment().getPaymentMethod(),
                vendorOrder.getOrder().getPayment().getTransactionId(),
                vendorOrder.getOrder().getPayment().getPaidAt()
        );

        return new VendorOrderDetailsResponseDto(
                vendorOrder.getId(),
                vendorOrder.getVendorOrderNumber(),
                vendorOrder.getVendor().getShopName(),
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
                vendorOrder.getVendorOrderStatus(),
                paymentInfoDto,
                items,
                vendorOrder.getCreatedAt()
        );
    }


    public static List<VendorOrderListResponseDto> toListDtoList(
            List<VendorOrder> vendorOrders) {

        return vendorOrders.stream()
                .map(VendorOrderMapper::toListDto)
                .toList();
    }
}