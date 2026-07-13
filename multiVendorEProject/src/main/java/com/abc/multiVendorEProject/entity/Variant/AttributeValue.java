package com.abc.multiVendorEProject.entity.Variant;

import com.abc.multiVendorEProject.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "attribute_values")
@Setter @Getter
public class AttributeValue extends BaseEntity {


    private String value; // Red, Blue, XL, 8GB

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;
}