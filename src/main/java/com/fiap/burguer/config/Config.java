package com.fiap.burguer.config;
import com.fiap.burguer.repository.CategoryRepository;
import com.fiap.burguer.repository.OrderRepository;
import com.fiap.burguer.repository.ProductRepository;
import com.fiap.burguer.repository.StatusRepository;
import com.fiap.burguer.service.CategoryService;
import com.fiap.burguer.service.OrderService;
import com.fiap.burguer.service.ProductService;
import com.fiap.burguer.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    StatusRepository statusRepository;

    @Bean
    public ProductService getProductService(){
        return new ProductService(productRepository);
    }

    @Bean
    public OrderService getOrderService(){
        return new OrderService(orderRepository);
    }

    @Bean
    public CategoryService getCategoryService() {
        return new CategoryService(categoryRepository);
    }

    @Bean
    public StatusService getStatusService() {
        return new StatusService(statusRepository);
    }
}
