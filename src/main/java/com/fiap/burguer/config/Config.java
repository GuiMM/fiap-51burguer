package com.fiap.burguer.config;
import com.fiap.burguer.adapter.driven.adapters.ClientAdapter;
import com.fiap.burguer.adapter.driven.adapters.OrderAdapter;
import com.fiap.burguer.adapter.driven.adapters.ProductAdapter;
import com.fiap.burguer.adapter.driven.repository.CheckoutRepository;
import com.fiap.burguer.adapter.driven.repository.OrderRepository;
import com.fiap.burguer.adapter.driven.repository.ProductRepository;
import com.fiap.burguer.adapter.driven.adapters.PaymentGateway;
import com.fiap.burguer.core.application.service.CheckoutService;
import com.fiap.burguer.core.application.service.ClientService;
import com.fiap.burguer.core.application.service.OrderService;
import com.fiap.burguer.core.application.service.ProductService;
import org.modelmapper.ModelMapper;
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
    ProductAdapter productAdapter;

    @Autowired
    OrderAdapter orderAdapter;

    @Autowired
    ClientAdapter clientAdapter;

    @Autowired
    CheckoutRepository checkoutRepository;

    @Autowired
    PaymentGateway paymentGateway;


    @Bean
    public ProductService getProductService() {
        return new ProductService(productAdapter);
    }

    @Bean
    public OrderService getOrderService() {
        return new OrderService(orderAdapter, productAdapter);
    }

    @Bean
    public ClientService getClientService() {
        return new ClientService(clientAdapter);
    }

    @Bean
    public CheckoutService getCheckoutService() {
        return new CheckoutService(checkoutRepository, orderRepository, paymentGateway);
    }

    @Bean
    public PaymentGateway getPaymentGateway() {
        return new PaymentGateway();
    }

}
