package com.fiap.burguer.service;

import com.fiap.burguer.entities.Category;
import com.fiap.burguer.entities.Product;
import com.fiap.burguer.enums.CategoryProduct;
import com.fiap.burguer.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ProductService  {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProductOrUpdate(Product product) {
        return productRepository.save(product);
    }

    public Product findById(int id) {
        return productRepository.findById(id);
    }

    public List<Product> findByCategory(CategoryProduct category) {
        List<Product> allProducts = productRepository.findAll();
        return allProducts.stream()
                .filter(product -> product.getCategory() == category)
                .collect(Collectors.toList());
    }

    public void deleteById(int id) {
        productRepository.deleteById(id);
    }
}
