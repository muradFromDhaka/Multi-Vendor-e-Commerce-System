package com.abc.multiVendorEProject.entity.Variant;

import com.abc.multiVendorEProject.entity.BaseEntity;
import com.abc.multiVendorEProject.entity.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "attributes",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"category_id", "name"}
                )
        }
)
@Setter @Getter
public class Attribute extends BaseEntity {

    @Column(nullable = false)
    private String name; // Color, Size, RAM

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}