package com.abc.multiVendorEProject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "banners")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Banner extends BaseEntity{

    private String title;

    private String subtitle;

    private String buttonText;

    private String buttonLink;

    private String imageUrl;

    private Integer displayOrder;

    private Boolean active;
}