package com.abc.multiVendorEProject.DTOs.projectDtos;

import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.AttributeValueResponseDTO;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDTO {

    private Long orderId;

    private Long vendorOrderId;

    private Long vendorId;

    private String vendorName;

    private String productName;

    private Long productId;

    private Long variantId;

    private String variantName;

    private String sku;

    private String imageUrl;

    private BigDecimal unitPrice;

    private Integer quantity;

    private BigDecimal totalPrice;

    private List<AttributeValueResponseDTO> attributes;


}
