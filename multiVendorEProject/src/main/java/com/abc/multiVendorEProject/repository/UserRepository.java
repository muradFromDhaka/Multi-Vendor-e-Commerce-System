package com.abc.multiVendorEProject.repository;

import com.abc.multiVendorEProject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("""
      SELECT COUNT(u)
      FROM User u
      JOIN u.roles r
      WHERE r.roleName = :roleName
      AND u.dateCreated BETWEEN :start AND :end
      AND u.deleted = false
       """)
    long countByRolesAndDateCreatedBetween(
            @Param("roleName") String roleName,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    Optional<User> findByUserNameAndDeletedFalse(String userName);
    Optional<User> findByEmailAndDeletedFalse(String email);

    boolean existsByUserNameIgnoreCase(String userName);
    boolean existsByEmailIgnoreCase(String email);

    Page<User> findByRolesRoleNameAndDeletedFalse(String roleName, Pageable pageable);

    @Query("""
        SELECT DISTINCT u
        FROM User u
        JOIN u.roles r
        WHERE r.roleName = :roleName
        AND u.deleted = false
        AND (
        LOWER(u.userName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
        OR LOWER(u.userFirstName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
        OR LOWER(u.userLastName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
        OR LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
         """)
    Page<User> searchUsersByRole(
            @Param("roleName") String roleName,
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );


//    Count User
    long countByRolesRoleNameAndDeletedFalse(String roleName);

    Optional<User> findByUserNameAndRolesRoleNameAndDeletedFalse(
            String userName,
            String roleName);


//    Active Customer Count
long countByRolesRoleNameAndEnabledTrueAndDeletedFalse(String roleName);

//    Disabled Customer Count
long countByRolesRoleNameAndEnabledFalseAndDeletedFalse(String roleName);


//    Active Customer List
Page<User> findByRolesRoleNameAndEnabledTrueAndDeletedFalse(
        String roleName,
        Pageable pageable);


//    Disabled Customer List
Page<User> findByRolesRoleNameAndEnabledFalseAndDeletedFalse(
        String roleName,
        Pageable pageable);

}
