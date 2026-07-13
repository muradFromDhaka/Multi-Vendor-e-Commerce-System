package com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto;


import com.abc.multiVendorEProject.enums.OrderStatus;

public record UpdateOrderStatusRequestDto(OrderStatus orderStatus ) { }
