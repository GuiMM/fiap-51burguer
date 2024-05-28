package com.fiap.burguer.core.application.service;

import com.fiap.burguer.core.application.dto.ProductCreate;
import com.fiap.burguer.adapter.driven.entities.Product;
import com.fiap.burguer.core.application.enums.CategoryProduct;
import com.fiap.burguer.adapter.driven.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(ProductCreate productCreate) {
        Product product = new Product();
        product.setName(productCreate.getName());
        product.setCategory(productCreate.getCategory());
        product.setPrice(productCreate.getPrice());
        product.setDescription(productCreate.getDescription());
        product.setPreparationTime(productCreate.getPreparationTime());
        product.setImage(productCreate.getImage());

        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        Integer productId = product.getId();

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + productId + " not found"));

        if (product.getName() != null) {
            existingProduct.setName(product.getName());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPreparationTime(product.getPreparationTime());
            existingProduct.setImage(product.getImage());
        }

        return productRepository.save(existingProduct);
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
