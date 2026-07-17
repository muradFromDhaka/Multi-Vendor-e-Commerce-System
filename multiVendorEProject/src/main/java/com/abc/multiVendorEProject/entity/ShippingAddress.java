package com.abc.multiVendorEProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ShippingAddresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddress extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_name", nullable = false)
    private User user;

    private String phone;

    private String street;
    private String city;
    private String district;
    private String country;
    private String zipCode;

}