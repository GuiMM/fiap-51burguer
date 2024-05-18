package com.fiap.burguer.config;

import com.fiap.burguer.repository.ProductRepository;
import com.fiap.burguer.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Autowired
    ProductRepository productRepository;

    @Bean
    public ProductService getProductService(){
        return new ProductService(productRepository);
    }
}