package com.abc.multiVendorEProject.mapper.Vendor;

import com.abc.multiVendorEProject.DTOs.projectDtos.Vendor.Customer.VendorCustomerDetailsResponseDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.Vendor.Customer.VendorCustomerOrderItemResponseDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.Vendor.Customer.VendorCustomerOrderResponseDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.Vendor.Customer.VendorCustomerResponseDTO;
import com.abc.multiVendorEProject.entity.Order;
import com.abc.multiVendorEProject.entity.OrderItem;
import com.abc.multiVendorEProject.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public final class VendorCustomerMapper {

    private VendorCustomerMapper() {
    }


    public static VendorCustomerResponseDTO toCustomerResponse(
            User user,
            String phone,
            Long totalOrders,
            BigDecimal totalSpent,
            LocalDateTime lastOrderDate) {

        return new VendorCustomerResponseDTO(
                user.getUserName(),
                user.getUserFirstName() + " " + user.getUserLastName(),
                user.getEmail(),
                phone,
                totalOrders,
                totalSpent,
                lastOrderDate
        );
    }


    public static VendorCustomerDetailsResponseDTO toCustomerDetailsResponse(
            User user,
            String phone,
            Long totalOrders,
            BigDecimal totalSpent,
            LocalDateTime lastOrderDate,
            List<VendorCustomerOrderResponseDTO> orders) {

        return new VendorCustomerDetailsResponseDTO(
                user.getUserName(),
                user.getUserFirstName() + " " + user.getUserLastName(),
                user.getEmail(),
                phone,
                totalOrders,
                totalSpent,
                lastOrderDate,
                orders
        );
    }



    public static VendorCustomerOrderItemResponseDTO toOrderItemResponse(
            OrderItem item) {

        return new VendorCustomerOrderItemResponseDTO(
                item.getVariant().getProduct().getId(),
                item.getVariant().getProduct().getName(),

                item.getVariant().getId(),
                item.getVariant().getSku(),

                item.getQuantity(),
                item.getUnitPrice(),
                item.getTotalPrice()
        );
    }


    public static VendorCustomerOrderResponseDTO toOrderResponse(
            Order order,
            BigDecimal vendorTotalPrice,
            List<VendorCustomerOrderItemResponseDTO> items) {

        return new VendorCustomerOrderResponseDTO(
                order.getId(),
                order.getOrderNumber(),

                order.getOrderStatus(),
                order.getPayment().getPaymentStatus(),
                order.getPayment().getPaymentMethod(),

                vendorTotalPrice,

                order.getCreatedAt(),

                items
        );
    }


}