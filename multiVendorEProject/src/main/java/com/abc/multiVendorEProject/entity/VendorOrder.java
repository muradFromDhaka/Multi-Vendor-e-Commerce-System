package com.abc.multiVendorEProject.entity;


import com.abc.multiVendorEProject.enums.OrderStatus;
import com.abc.multiVendorEProject.enums.PaymentMethod;
import com.abc.multiVendorEProject.enums.PaymentStatus;
import com.abc.multiVendorEProject.enums.VendorOrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "vendor_orders")
public class VendorOrder extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @OneToMany(mappedBy = "vendorOrder",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems= new ArrayList<>();

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal shippingFee;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal discount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VendorOrderStatus status = VendorOrderStatus.PENDING;

    private String vendorOrderNumber;

    private OrderStatus orderStatus;

    private PaymentStatus paymentStatus;

    private PaymentMethod paymentMethod;
}

