package com.abc.multiVendorEProject.DTOs.projectDtos;

import java.time.LocalDateTime;

public record DealRequestDto(
         String title,
         Integer discountPercent,
         LocalDateTime startTime,
         LocalDateTime endTime,
         Long productId
) {}
