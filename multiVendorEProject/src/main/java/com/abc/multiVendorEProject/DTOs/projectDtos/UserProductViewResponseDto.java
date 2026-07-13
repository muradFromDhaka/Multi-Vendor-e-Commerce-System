package com.abc.multiVendorEProject.DTOs.projectDtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserProductViewResponseDto {

    private Long id;

    private String userName;

    private Long productId;

    private String productName;

    private LocalDateTime viewedAt;
}