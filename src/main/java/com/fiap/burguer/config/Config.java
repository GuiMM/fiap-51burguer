package com.fiap.burguer.config;

import com.fiap.burguer.repository.CategoryRepository;
import com.fiap.burguer.repository.ProductRepository;
import com.fiap.burguer.service.CategoryService;
import com.fiap.burguer.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Bean
    public ProductService getProductService(){
        return new ProductService(productRepository);
    }

    @Bean
    public CategoryService getCategoryService() {
        return new CategoryService(categoryRepository);
    }
}
