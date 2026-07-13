package com.abc.multiVendorEProject.DTOs.securityDtos;



import com.abc.multiVendorEProject.entity.Role;

import java.util.List;

public record AdminStatistics(long totalUsers, long enabledUsers, List<Role> roles) {
}
