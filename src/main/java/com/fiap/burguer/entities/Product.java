package com.fiap.burguer.entities;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

//    @OneToOne
//    @JoinColumn(name = "category")
//    private CategoryProduct category;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "preparationTime", nullable = false)
    private Integer preparationTime;

    @Column(name = "image", nullable = true)
    private String image;

    public Product(){}
    Product(int id,
            String name,
//            CategoryProduct category,
            double price,
            String description,
            Integer preparationTime,
            String image) {
        this.id = id;
        this.name = name;
//        this.category = category;
        this.price = price;
        this.description = description;
        this.preparationTime = preparationTime;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public CategoryProduct getCategory() {
//        return category;
//    }
//
//    public void setCategory(CategoryProduct category) {
//        this.category = category;
//    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}