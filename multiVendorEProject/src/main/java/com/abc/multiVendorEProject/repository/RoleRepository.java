package com.abc.multiVendorEProject.repository;

import com.abc.multiVendorEProject.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findByRoleName(String roleName);

    boolean existsByRoleNameIgnoreCase(String roleName);

}
