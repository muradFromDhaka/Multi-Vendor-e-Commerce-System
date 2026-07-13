package com.abc.multiVendorEProject.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(nullable = false, updatable = false)
    private String userName;

    @Column
    private String userFirstName;

    @Column
    private String userLastName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean deleted = false;

    // Initialize all Boolean fields with default values
    @Column
    private Boolean enabled = true;

    @Column
    private Boolean credentialsNonExpired = true;

    @Column
    private Boolean accountNonExpired = true;

    @Column
    private Boolean accountNonLocked = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "userrole",
            joinColumns = @JoinColumn(name = "user_name"),
            inverseJoinColumns = @JoinColumn(name = "role_name")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Address> addressList;

//    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreated;

//    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime lastUpdated;

    @PrePersist
    public void prePersist() {
        this.dateCreated = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdated = LocalDateTime.now();
    }

//
//    public User(String userName, String email, String password) {
//        this.userName = userName;
//        this.password = password;
//        this.email = email;
//        // Initialize Booleans in constructor too
//        this.enabled = true;
//        this.credentialsNonExpired = true;
//        this.accountNonExpired = true;
//        this.accountNonLocked = true;
//    }
//
//    public User() {
//        // Initialize in default constructor
//        this.enabled = true;
//        this.credentialsNonExpired = true;
//        this.accountNonExpired = true;
//        this.accountNonLocked = true;
//    }

}
