package com.abc.multiVendorEProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="wishlists")
public class Wishlist extends BaseEntity{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;
    
    
    // Wishlist can contain many products
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "wishlist_products",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"),
            uniqueConstraints = {
                    @UniqueConstraint(
                            columnNames = {
                                    "wishlist_id",
                                    "product_id"
                            }
                    )
            }
    )
    private Set<Product> products = new HashSet<>();
    
}
