package com.abc.multiVendorEProject.entity;

import com.abc.multiVendorEProject.enums.VendorStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "vendors")
public class Vendor extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String shopName;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private VendorStatus status = VendorStatus.PENDING;

    private Double rating = 0.0;

    @OneToOne
    @JoinColumn(
            name = "username",               // FK column in vendors table
            referencedColumnName = "userName",
            nullable = false
    )
    private User user;

    // ---------------- Profile Fields ----------------
    private String businessEmail;
    private String phone;
    private String address;
    private String logoUrl;
    private String bannerUrl;
}
