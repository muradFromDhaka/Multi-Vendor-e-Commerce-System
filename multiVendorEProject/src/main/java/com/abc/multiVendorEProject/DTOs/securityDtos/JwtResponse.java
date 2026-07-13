package com.abc.multiVendorEProject.DTOs.securityDtos;


import com.abc.multiVendorEProject.entity.User;

public record JwtResponse(
        String jwtToken,
        User user
//        String username,
//        String email,
//        Collection<String> roles
) {}