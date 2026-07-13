package com.abc.multiVendorEProject.service.Admin;

import com.abc.multiVendorEProject.DTOs.projectDtos.Customer.CustomerStatisticsDto;
import com.abc.multiVendorEProject.DTOs.securityDtos.UserResponseDto;
import com.abc.multiVendorEProject.Util.NotFoundException;
import com.abc.multiVendorEProject.Util.RoleConstants;
import com.abc.multiVendorEProject.entity.User;
import com.abc.multiVendorEProject.mapper.UserMapper;
import com.abc.multiVendorEProject.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class AdminUserService {
    private final UserRepository userRepository;

//    ===========================Helper Class================================
private User getCustomerEntity(String username) {
    return userRepository
            .findByUserNameAndRolesRoleNameAndDeletedFalse(
                    username,
                    RoleConstants.ROLE_USER)
            .orElseThrow(NotFoundException::new);
}

    public UserResponseDto getCustomer(String username) {
        return UserMapper.mapToDTO(getCustomerEntity(username));
    }


    public Page<UserResponseDto> getAllCustomers(Pageable pageable) {

        return userRepository
                .findByRolesRoleNameAndDeletedFalse(
                        RoleConstants.ROLE_USER,
                        pageable)
                .map(UserMapper::mapToDTO);
    }


    public Page<UserResponseDto> searchCustomers(
            String searchTerm,
            Pageable pageable) {

        return userRepository
                .searchUsersByRole(
                        RoleConstants.ROLE_USER,
                        searchTerm,
                        pageable
                )
                .map(UserMapper::mapToDTO);
    }


    public void enableCustomer(String username) {

        User user = getCustomerEntity(username);

        user.setEnabled(true);
        userRepository.save(user);
    }

    public void disableCustomer(String username) {
        User user = getCustomerEntity(username);

        user.setEnabled(false);
        userRepository.save(user);
    }

    public void deleteCustomer(String username) {

        User user = getCustomerEntity(username);

        user.setDeleted(true);

        userRepository.save(user);
    }

    public Page<UserResponseDto> getActiveCustomers(Pageable pageable) {

        return userRepository
                .findByRolesRoleNameAndEnabledTrueAndDeletedFalse(
                        RoleConstants.ROLE_USER,
                        pageable
                )
                .map(UserMapper::mapToDTO);
    }


    public Page<UserResponseDto> getDisabledCustomers(Pageable pageable) {

        return userRepository
                .findByRolesRoleNameAndEnabledFalseAndDeletedFalse(
                        RoleConstants.ROLE_USER,
                        pageable
                )
                .map(UserMapper::mapToDTO);
    }


    public CustomerStatisticsDto getCustomerStatistics() {

        return new CustomerStatisticsDto(
                userRepository.countByRolesRoleNameAndDeletedFalse(RoleConstants.ROLE_USER),
                userRepository.countByRolesRoleNameAndEnabledTrueAndDeletedFalse(RoleConstants.ROLE_USER),
                userRepository.countByRolesRoleNameAndEnabledFalseAndDeletedFalse(RoleConstants.ROLE_USER)
        );
    }

}
