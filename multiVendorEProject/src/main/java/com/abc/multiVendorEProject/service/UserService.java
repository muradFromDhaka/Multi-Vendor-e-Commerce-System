package com.abc.multiVendorEProject.service;

import com.abc.multiVendorEProject.DTOs.securityDtos.UserResponseDto;
import com.abc.multiVendorEProject.Util.NotFoundException;
import com.abc.multiVendorEProject.Util.RoleConstants;
import com.abc.multiVendorEProject.entity.Role;
import com.abc.multiVendorEProject.entity.User;
import com.abc.multiVendorEProject.mapper.UserMapper;
import com.abc.multiVendorEProject.repository.RoleRepository;
import com.abc.multiVendorEProject.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Value("${app.admin.email}")
    private String adminEmail;

    @PostConstruct
    public void init() {
        initRoleAndUser();
    }

    public void initRoleAndUser() {

        if (!roleRepository.existsById(RoleConstants.ROLE_ADMIN)) {

            Role adminRole = new Role();
            adminRole.setRoleName(RoleConstants.ROLE_ADMIN);
            adminRole.setRoleDescription("Admin role");
            adminRole.setDateCreated(OffsetDateTime.now());
            adminRole.setLastUpdated(OffsetDateTime.now());
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setRoleName(RoleConstants.ROLE_USER);
            userRole.setRoleDescription("Default role for newly created record");
            userRole.setDateCreated(OffsetDateTime.now());
            userRole.setLastUpdated(OffsetDateTime.now());
            roleRepository.save(userRole);

            Role vendorRole = new Role();
            vendorRole.setRoleName(RoleConstants.ROLE_VENDOR);
            vendorRole.setRoleDescription("Default role for newly created record");
            vendorRole.setDateCreated(OffsetDateTime.now());
            vendorRole.setLastUpdated(OffsetDateTime.now());
            roleRepository.save(vendorRole);

            Role roleMODERATOR = new Role();
            roleMODERATOR.setRoleName(RoleConstants.ROLE_MODERATOR);
            roleMODERATOR.setRoleDescription("Default role for newly ROLE_MODERATOR record");
            roleMODERATOR.setDateCreated(OffsetDateTime.now());
            roleMODERATOR.setLastUpdated(OffsetDateTime.now());
            roleRepository.save(roleMODERATOR);

        }

        if (!userRepository.existsByUserNameIgnoreCase(adminUsername)) {
        User adminUser = new User();
        adminUser.setUserName(adminUsername);
        adminUser.setPassword(passwordEncoder.encode(adminPassword));
        adminUser.setUserFirstName("murad");
        adminUser.setUserLastName("murad");
        adminUser.setEmail(adminEmail);

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(roleRepository.findById(RoleConstants.ROLE_ADMIN).orElseThrow());
        adminRoles.add(roleRepository.findById(RoleConstants.ROLE_MODERATOR).orElseThrow());

        adminUser.setRoles(adminRoles);
        userRepository.save(adminUser);

        }
    }

    public UserResponseDto get(final String userName) {
        return userRepository.findByUserNameAndDeletedFalse(userName)
                .map((user01) -> UserMapper.mapToDTO(user01))
                .orElseThrow(NotFoundException::new);
    }

    public String create(UserResponseDto userDTO) {

        if (userRepository.existsByUserNameIgnoreCase(userDTO.getUserName())) {
            throw new RuntimeException("Username already exists.");
        }

        if (userRepository.existsByEmailIgnoreCase(userDTO.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        User user = new User();

        UserMapper.mapToEntity(userDTO, user);

        user.setUserName(userDTO.getUserName());

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        List<String> roleNames = userDTO.getRoles() == null
                ? Collections.emptyList()
                : userDTO.getRoles();

        List<Role> roles = roleRepository.findAllById(roleNames);

        if (roles.size() != roleNames.size()) {
            throw new NotFoundException("One or more roles not found.");
        }

        user.setRoles(new HashSet<>(roles));
        return userRepository.save(user).getUserName();
    }

    public void update(String userName, UserResponseDto userDTO) {

        User user = userRepository.findByUserNameAndDeletedFalse(userName)
                .orElseThrow(NotFoundException::new);

        if (!user.getEmail().equalsIgnoreCase(userDTO.getEmail())
                && userRepository.existsByEmailIgnoreCase(userDTO.getEmail())) {

            throw new RuntimeException("Email already exists.");
        }

        UserMapper.mapToEntity(userDTO, user);

        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        List<String> roleNames = userDTO.getRoles() == null
                ? Collections.emptyList()
                : userDTO.getRoles();

        List<Role> roles = roleRepository.findAllById(roleNames);

        if (roles.size() != roleNames.size()) {
            throw new NotFoundException("One or more roles not found.");
        }

        user.setRoles(new HashSet<>(roles));
        userRepository.save(user);
    }

    public void delete(String userName) {

        User user = userRepository.findByUserNameAndDeletedFalse(userName)
                .orElseThrow(NotFoundException::new);

        user.setDeleted(true);

        userRepository.save(user);
    }

//    public boolean userNameExists(final String userName) {
//        return userRepository.existsByUserNameIgnoreCase(userName);
//    }
//
//    public boolean emailExists(final String email) {
//        return userRepository.existsByEmailIgnoreCase(email);
//    }

//    public Page<UserResponseDto> getAllUsers(Pageable pageable) {
//
//        Page<User> userPage = userRepository.findByRolesRoleNameAndDeletedFalse(
//                RoleConstants.ROLE_USER,
//                pageable
//        );
//
//        return userPage.map(UserMapper::mapToDTO);
//    }


//    public Page<UserResponseDto> searchUsers(String searchTerm, Pageable pageable) {
//
//        Page<User> userPage = userRepository.searchUsersByRole(
//                RoleConstants.ROLE_USER,
//                searchTerm,
//                pageable
//        );
//        return userPage.map((user01) -> UserMapper.mapToDTO(user01));
//    }


}
