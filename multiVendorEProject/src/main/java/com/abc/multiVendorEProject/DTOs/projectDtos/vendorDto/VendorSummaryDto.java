package com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorSummaryDto {

    private long all;

    private long pending;

    private long approved;

    private long active;

    private long suspended;

    private long rejected;

}