package com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto;

import com.abc.multiVendorEProject.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {

    @NotBlank(message = "Product name is required")
    @Size(max = 100, message = "Product name must be less than 100 characters")
    private String name;

    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    @NotNull(message = "Category is required")
    private Long categoryId;

    @NotNull(message = "brand is required")
    private Long brandId;

    // Optional fields
    private Long vendorId;       // admin can set
    private ProductStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
//    private LocalDate releaseDate;

    private List<String> imageUrls; // optional images
}
