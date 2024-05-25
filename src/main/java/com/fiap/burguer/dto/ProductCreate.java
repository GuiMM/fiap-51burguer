package com.fiap.burguer.dto;

import com.fiap.burguer.enums.CategoryProduct;
import lombok.Data;

@Data
public class ProductCreate {
    private String name;
    private CategoryProduct category;
    private double price;
    private String description;
    private Integer preparationTime;
    private String image;

    public ProductCreate(String name, String image, Integer preparationTime, String description, double price, CategoryProduct category) {
        this.name = name;
        this.image = image;
        this.preparationTime = preparationTime;
        this.description = description;
        this.price = price;
        this.category = category;
    }


}
