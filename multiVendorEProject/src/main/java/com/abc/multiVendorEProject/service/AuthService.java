package com.abc.multiVendorEProject.service;

import com.abc.multiVendorEProject.DTOs.securityDtos.JwtResponse;
import com.abc.multiVendorEProject.DTOs.securityDtos.LoginRequest;
import com.abc.multiVendorEProject.DTOs.securityDtos.UserRequestDto;
import com.abc.multiVendorEProject.entity.Role;
import com.abc.multiVendorEProject.entity.User;
import com.abc.multiVendorEProject.repository.RoleRepository;
import com.abc.multiVendorEProject.repository.UserRepository;
import com.abc.multiVendorEProject.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);


        User user = userRepository.findByUserNameAndDeletedFalse(loginRequest.username())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String jwt = jwtUtil.generateTokenFromUsername(user);

//        Collection<String> roles = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());

        return new JwtResponse(
                jwt,
                user
        );
    }

    public String register(UserRequestDto registerRequest) {
        validateRegistration(registerRequest);

        User user = createUserFromRequest(registerRequest);
        setUserRoles(user, null);

        userRepository.save(user);
        return "User registered successfully";
    }

    public String initializeRoles() {
        String[] defaultRoles = {"USER", "ADMIN", "MODERATOR","VENDOR"};
        for (String roleName : defaultRoles) {
            if (!roleRepository.existsById(roleName)) {
                Role role = new Role();
                role.setRoleName(roleName);
                role.setRoleDescription(roleName + " Role");
                roleRepository.save(role);
            }
        }
        return "Roles initialized successfully";
    }

    private void validateRegistration(UserRequestDto registerRequest) {
        if (userRepository.existsByUserNameIgnoreCase(registerRequest.username())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmailIgnoreCase(registerRequest.email())) {
            throw new IllegalArgumentException("Email already exists");
        }
    }

    private User createUserFromRequest(UserRequestDto registerRequest) {
        User user = new User();
        user.setUserName(registerRequest.username());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setEmail(registerRequest.email());
        user.setUserFirstName(registerRequest.firstName());
        user.setUserLastName(registerRequest.lastName());

        // Set default values
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);

        return user;
    }

    private void setUserRoles(User user, Set<String> roleNames) {
        Set<Role> roles = new HashSet<>();

        if (roleNames == null || roleNames.isEmpty()) {
            // Default role
            Role userRole = roleRepository.findByRoleName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Error: Role USER not found."));
            roles.add(userRole);
        } else {
            roleNames.forEach(roleName -> {
                Role foundRole = roleRepository.findByRoleName(roleName)
                        .orElseThrow(() -> new RuntimeException("Error: Role " + roleName + " not found."));
                roles.add(foundRole);
            });
        }

        user.setRoles(roles);
    }
}