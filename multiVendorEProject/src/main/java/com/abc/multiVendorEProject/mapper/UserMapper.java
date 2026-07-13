package com.abc.multiVendorEProject.mapper;

import com.abc.multiVendorEProject.DTOs.securityDtos.UserResponseDto;
import com.abc.multiVendorEProject.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserResponseDto mapToDTO(User user) {

        UserResponseDto dto = new UserResponseDto();

        dto.setUserName(user.getUserName());
        dto.setUserFirstName(user.getUserFirstName());
        dto.setUserLastName(user.getUserLastName());
        dto.setEmail(user.getEmail());

        dto.setEnabled(user.getEnabled());
        dto.setCredentialsNonExpired(user.getCredentialsNonExpired());
        dto.setAccountNonExpired(user.getAccountNonExpired());
        dto.setAccountNonLocked(user.getAccountNonLocked());

        if (user.getRoles() != null) {
            dto.setRoles(
                    user.getRoles()
                            .stream()
                            .map(role -> role.getRoleName())
                            .toList()
            );
        }

        return dto;
    }

    public static void mapToEntity(UserResponseDto dto, User user) {

        user.setUserFirstName(dto.getUserFirstName());
        user.setUserLastName(dto.getUserLastName());
        user.setEmail(dto.getEmail());

        user.setEnabled(dto.getEnabled());
        user.setCredentialsNonExpired(dto.getCredentialsNonExpired());
        user.setAccountNonExpired(dto.getAccountNonExpired());
        user.setAccountNonLocked(dto.getAccountNonLocked());
    }

}