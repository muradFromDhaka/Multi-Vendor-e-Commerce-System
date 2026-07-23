package com.abc.multiVendorEProject.entity;

import com.abc.multiVendorEProject.enums.VendorStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
            name = "username",
            referencedColumnName = "userName",
            nullable = false
    )
    private User user;

    @OneToMany(mappedBy = "vendor")
    private List<VendorOrder> vendorOrders = new ArrayList<>();

    // ---------------- Profile Fields ----------------
    private String businessEmail;
    private String phone;
    private String address;
    private String logoUrl;
    private String bannerUrl;
}
