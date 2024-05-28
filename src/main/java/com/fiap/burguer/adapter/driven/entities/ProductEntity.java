package com.fiap.burguer.adapter.driven.entities;

import com.fiap.burguer.core.application.enums.CategoryProduct;
import com.fiap.burguer.core.domain.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "product")
@Data
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryProduct category;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "preparation_time", nullable = false)
    private Integer preparationTime;

    @Column(name = "image", nullable = true)
    private String image;

}
