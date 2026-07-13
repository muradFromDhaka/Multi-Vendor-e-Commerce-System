package com.abc.multiVendorEProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_name", nullable = false)
    private User user;

    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CartItem> items = new ArrayList<>();


    // Total amount of the cart. This should be updated whenever items are added/removed.
    private BigDecimal totalAmount;


    public void calculateTotalAmount() {
        this.totalAmount = this.items.stream()
                .map(CartItem::calculateTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
