package com.fiap.burguer.config;
import com.fiap.burguer.core.application.ports.IPaymentGateway;
import com.fiap.burguer.core.application.usecases.CheckoutUseCases;
import com.fiap.burguer.core.application.usecases.ClientUseCases;
import com.fiap.burguer.core.application.usecases.OrderUseCases;
import com.fiap.burguer.core.application.usecases.ProductUseCases;
import com.fiap.burguer.infraestructure.adapters.*;
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
    public ProductUseCases getProductService() {
        return new ProductUseCases(productAdapter);
    }

    @Bean
    public OrderUseCases getOrderService() {
        return new OrderUseCases(orderAdapter, productAdapter);
    }

    @Bean
    public ClientUseCases getClientService() {
        return new ClientUseCases(clientAdapter);
    }

    @Bean
    public CheckoutUseCases getCheckoutService() {
        return new CheckoutUseCases(checkoutAdapter, orderAdapter, paymentGateway);
    }

    @Bean
    public IPaymentGateway getPaymentGateway() {
        return new PaymentGateway();
    }

}
