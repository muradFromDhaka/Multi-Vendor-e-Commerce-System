package com.abc.multiVendorEProject.DTOs.securityDtos;

public record UserRequestDto(
        String username,
        String password,
        String email,
        String firstName,
        String lastName
       
) {}
