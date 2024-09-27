package com.fiap.burguer.config;
import com.fiap.burguer.core.application.ports.IPaymentGateway;
import com.fiap.burguer.core.application.usecases.*;
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
    public ValidateOrderUseCase validateOrderUseCase() {
        return new ValidateOrderUseCase();
    }

    @Bean
    public GetClientOrderUseCase getClientOrderUseCase() {
        return new GetClientOrderUseCase( clientAdapter);
    }
    @Bean
    public CreateOrderUseCase createOrderUseCase() {
        return new CreateOrderUseCase(orderAdapter,validateOrderUseCase(), getClientOrderUseCase(),productAdapter,timeWaitingOrderQueueUseCase());
    }

    @Bean
    public GetOrderByIdUseCase getOrderByIdUseCase() {
        return new GetOrderByIdUseCase(orderAdapter);
    }

    @Bean
    public GetAllOrdersUseCase getAllOrdersUseCase() {
        return new GetAllOrdersUseCase(orderAdapter);
    }

    @Bean
    public TimeWaitingOrderQueueUseCase timeWaitingOrderQueueUseCase() {
        return new TimeWaitingOrderQueueUseCase(getOrdersByStatusUseCase());
    }

    @Bean
    public OrdersStatusUseCase getOrdersByStatusUseCase() {
        return new OrdersStatusUseCase(orderAdapter);
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
