package com.abc.multiVendorEProject.mapper.Customer;

import com.abc.multiVendorEProject.DTOs.projectDtos.Vendor.Customer.CustomerVendorOrderDto;
import com.abc.multiVendorEProject.entity.VendorOrder;
import com.abc.multiVendorEProject.mapper.OrderItemMapper;

public class CustomerVendorOrderMapper {

    private CustomerVendorOrderMapper() {
    }

    public static CustomerVendorOrderDto toCustomerDto(VendorOrder vendorOrder) {

        return new CustomerVendorOrderDto(

                vendorOrder.getId(),

                vendorOrder.getVendor().getId(),

                vendorOrder.getVendor().getShopName(),

                vendorOrder.getVendorOrderStatus(),

                vendorOrder.getOrderItems()
                        .stream()
                        .map(OrderItemMapper::toResponseDto)
                        .toList(),

                vendorOrder.getUpdatedAt()
        );
    }
}