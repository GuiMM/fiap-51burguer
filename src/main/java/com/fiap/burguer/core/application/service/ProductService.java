package com.fiap.burguer.core.application.service;

import com.fiap.burguer.core.application.dto.ProductCreate;
import com.fiap.burguer.adapter.driven.entities.ProductEntity;
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

    public ProductEntity saveProduct(ProductCreate productCreate) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productCreate.getName());
        productEntity.setCategory(productCreate.getCategory());
        productEntity.setPrice(productCreate.getPrice());
        productEntity.setDescription(productCreate.getDescription());
        productEntity.setPreparationTime(productCreate.getPreparationTime());
        productEntity.setImage(productCreate.getImage());

        return productRepository.save(productEntity);
    }

    public ProductEntity updateProduct(ProductEntity productEntity) {
        Integer productId = productEntity.getId();

        ProductEntity existingProductEntity = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + productId + " not found"));

        if (productEntity.getName() != null) {
            existingProductEntity.setName(productEntity.getName());
            existingProductEntity.setCategory(productEntity.getCategory());
            existingProductEntity.setPrice(productEntity.getPrice());
            existingProductEntity.setDescription(productEntity.getDescription());
            existingProductEntity.setPreparationTime(productEntity.getPreparationTime());
            existingProductEntity.setImage(productEntity.getImage());
        }

        return productRepository.save(existingProductEntity);
    }

    public ProductEntity findById(int id) {
        return productRepository.findById(id);
    }

    public List<ProductEntity> findByCategory(CategoryProduct category) {
        List<ProductEntity> allProductEntities = productRepository.findAll();
        return allProductEntities.stream()
                .filter(product -> product.getCategory() == category)
                .collect(Collectors.toList());
    }

    public void deleteById(int id) {
        productRepository.deleteById(id);
    }
}
