package com.fiap.burguer.core.application.service;

import com.fiap.burguer.core.application.dto.ProductCreate;
import com.fiap.burguer.core.application.enums.CategoryProduct;
import com.fiap.burguer.core.application.ports.ProductPort;
import com.fiap.burguer.core.domain.Product;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private final ProductPort productPort;

    public ProductService(ProductPort productPort) {
        this.productPort = productPort;
    }

    public Product saveProduct(ProductCreate productCreate) {
        Product product = new Product();
        product.setName(productCreate.getName());
        product.setCategory(productCreate.getCategory());
        product.setPrice(productCreate.getPrice());
        product.setDescription(productCreate.getDescription());
        product.setPreparationTime(productCreate.getPreparationTime());
        product.setImage(productCreate.getImage());

        return productPort.save(product);
    }

    public Product updateProduct(Product productEntity) {
        Integer productId = productEntity.getId();

        Product existingProduct = productPort.findById(productId);
        if (existingProduct == null) {
            throw new EntityNotFoundException("Product with ID " + productId + " not found");
        }

        if (productEntity.getName() != null) {
            existingProduct.setName(productEntity.getName());
            existingProduct.setCategory(productEntity.getCategory());
            existingProduct.setPrice(productEntity.getPrice());
            existingProduct.setDescription(productEntity.getDescription());
            existingProduct.setPreparationTime(productEntity.getPreparationTime());
            existingProduct.setImage(productEntity.getImage());
        }

        return productPort.save(existingProduct);
    }

    public Product findById(int id) {
        return productPort.findById(id);
    }

    public List<Product> findByCategory(CategoryProduct category) {
        List<Product> allProductEntities = productPort.findAll();
        return allProductEntities.stream()
                .filter(product -> product.getCategory() == category)
                .collect(Collectors.toList());
    }

    public void deleteById(int id) {
        productPort.deleteById(id);
    }
}
