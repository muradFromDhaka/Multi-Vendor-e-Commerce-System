package com.abc.multiVendorEProject.RestController;

import com.abc.multiVendorEProject.DTOs.securityDtos.UserResponseDto;
import com.abc.multiVendorEProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // GET USER BY USERNAME - Accessible by ADMIN or the user themselves
    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.principal.username")
    public ResponseEntity<UserResponseDto> getUserByUsername(@PathVariable String username) {

        UserResponseDto user = userService.get(username);

        return ResponseEntity.ok(user);

    }

    // UPDATE USER - Accessible by ADMIN or the user themselves
    @PutMapping("/{username}")
    public ResponseEntity<Void> updateUser(
            @PathVariable String username,
            @RequestBody UserResponseDto dto){

        userService.update(username,dto);

        return ResponseEntity.noContent().build();
    }


    // DELETE USER - Only accessible by ADMIN
    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {

        userService.delete(username);

        return ResponseEntity.noContent().build();
    }

}

