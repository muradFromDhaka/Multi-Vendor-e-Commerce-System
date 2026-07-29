package com.abc.multiVendorEProject.DTOs.projectDtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DealRequestDto(

        @NotBlank(message = "Title is required")
        String title,

        @NotNull(message = "Discount percent is required")
        @Min(value = 1, message = "Minimum discount is 1%")
        @Max(value = 90, message = "Maximum discount is 90%")
        Integer discountPercent,

        @NotNull(message = "Start time is required")
        LocalDateTime startTime,

        @NotNull(message = "End time is required")
        LocalDateTime endTime,

        @NotNull(message = "Product is required")
        Long productId

) {}