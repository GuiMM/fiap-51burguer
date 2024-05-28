package com.fiap.burguer.config;
import com.fiap.burguer.adapter.driven.adapters.*;
import com.fiap.burguer.core.application.service.CheckoutService;
import com.fiap.burguer.core.application.service.ClientService;
import com.fiap.burguer.core.application.service.OrderService;
import com.fiap.burguer.core.application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Autowired
    ProductAdapter productAdapter;

    @Autowired
    OrderAdapter orderAdapter;

    @Autowired
    ClientAdapter clientAdapter;

    @Autowired
    CheckOutAdapter checkoutAdapter;

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
        return new CheckoutService(checkoutAdapter, orderAdapter, paymentGateway);
    }

    @Bean
    public PaymentGateway getPaymentGateway() {
        return new PaymentGateway();
    }

}
