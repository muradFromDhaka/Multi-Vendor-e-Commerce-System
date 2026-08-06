package com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorDashboard;

import com.abc.multiVendorEProject.enums.VendorOrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecentVendorOrderDto {

    private Long vendorOrderId;

    private String orderNumber;

    private String customerName;

    private BigDecimal totalAmount;

    private VendorOrderStatus status;

    private LocalDateTime orderDate;

}
