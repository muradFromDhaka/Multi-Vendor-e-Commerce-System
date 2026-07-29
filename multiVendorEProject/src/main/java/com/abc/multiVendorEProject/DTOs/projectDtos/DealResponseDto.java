package com.abc.multiVendorEProject.DTOs.projectDtos;

import java.time.LocalDateTime;

public record DealResponseDto(

        Long id,

        String title,

        Integer discountPercent,

        Boolean active,

        LocalDateTime startTime,

        LocalDateTime endTime,

        Long productId,

        String productImage,

        String productName

) {}