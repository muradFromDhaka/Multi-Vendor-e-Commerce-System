package com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.VendorDashboard;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LatestReviewDto {

    private String customerName;

    private String productName;

    private Integer rating;

    private String comment;

    private LocalDateTime reviewDate;

}
