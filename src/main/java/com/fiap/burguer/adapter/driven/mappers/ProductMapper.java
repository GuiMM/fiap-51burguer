package com.fiap.burguer.adapter.driven.mappers;

import com.fiap.burguer.adapter.driven.entities.ProductEntity;
import com.fiap.burguer.core.domain.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    public static ProductEntity toEntity(Product product) {
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(product, productEntity);
        return productEntity;
    }

    public static Product toDomain(ProductEntity productEntity) {
        Product product = new Product();
        BeanUtils.copyProperties(productEntity, product);
        return product;
    }

    public static List<Product> toDomain(List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(ProductMapper::toDomain)
                .collect(Collectors.toList());
    }
}
