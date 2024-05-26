package com.fiap.burguer.config;
import com.fiap.burguer.adapters.PaymentGateway;
import com.fiap.burguer.ports.IPaymentGateway;
import com.fiap.burguer.repository.*;
import com.fiap.burguer.service.*;
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
    ClientRepository clientRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    CheckoutRepository checkoutRepository;

    @Autowired
    IPaymentGateway paymentGateway;

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
    public ClientService getClientService() { return new ClientService(clientRepository); }

    @Bean
    public StatusService getStatusService() {
        return new StatusService(statusRepository);
    }

    @Bean
    public CheckoutService getCheckoutService() {
        return new CheckoutService(checkoutRepository);
    }

    @Bean
    public IPaymentGateway paymentGateway() {return  new PaymentGateway();}
    
}
