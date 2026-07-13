package com.abc.multiVendorEProject.mapper;

import com.abc.multiVendorEProject.DTOs.projectDtos.OrderItemRequestDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.OrderItemResponseDTO;
import com.abc.multiVendorEProject.entity.OrderItem;
import com.abc.multiVendorEProject.mapper.Variant.AttributeValueMapper;

public class OrderItemMapper {

    public static OrderItem toEntity(OrderItemRequestDTO dto) {

        if (dto == null) {
            return null;
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(dto.getQuantity());

        return orderItem;
    }

    public static OrderItemResponseDTO toResponseDto(OrderItem orderItem) {

        if (orderItem == null) {
            return null;
        }

        return OrderItemResponseDTO.builder()
                .orderId(orderItem.getId())

                .productId(orderItem.getVariant().getProduct().getId())
                .variantId(orderItem.getVariant().getId())
                .vendorOrderId(
                        orderItem.getVendorOrder() != null
                                ? orderItem.getVendorOrder().getId()
                                : null
                )

                .productName(orderItem.getVariant().getProduct().getName())

                .imageUrl(
                        orderItem.getVariant()
                                .getProduct()
                                .getImageUrls()
                                .isEmpty()
                                ? null
                                : orderItem.getVariant()
                                .getProduct()
                                .getImageUrls()
                                .get(0)

                )

                .attributes(
                        orderItem.getVariant()
                                .getAttributeValues()
                                .stream()
                                .map(AttributeValueMapper::toResponse)
                                .toList()
                )

                .sku(orderItem.getVariant().getSku())

                .unitPrice(orderItem.getUnitPrice())
                .quantity(orderItem.getQuantity())
                .totalPrice(orderItem.getTotalPrice())

                .vendorId(orderItem.getVendor().getId())
                .vendorName(
                        orderItem.getVendor() != null
                                ? orderItem.getVendor().getShopName()
                                : null
                )

                .build();
    }
}