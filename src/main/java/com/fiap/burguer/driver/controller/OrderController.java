package com.fiap.burguer.driver.controller;
import com.fiap.burguer.api.OrderApi;
import com.fiap.burguer.core.application.usecases.*;
import com.fiap.burguer.driver.presenters.OrderPresenter;
import com.fiap.burguer.driver.dto.OrderRequest;
import com.fiap.burguer.driver.dto.OrderResponse;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.domain.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrderController implements OrderApi {
    private final ClientUseCases clientservice;
    private final CreateOrderUseCase createOrderUseCase;
    private final GetAllOrdersUseCase getAllOrdersUseCase;
    private final OrdersStatusUseCase ordersByStatusUseCase;
    private final GetOrderByIdUseCase getOrderByIdUseCase;

    public OrderController(ClientUseCases clientservice, CreateOrderUseCase createOrder , GetAllOrdersUseCase getAllOrders, OrdersStatusUseCase getOrdersByStatus, GetOrderByIdUseCase getOrderById ) {
        this.clientservice = clientservice;
        this.createOrderUseCase = createOrder;
        this.getAllOrdersUseCase = getAllOrders;
        this.ordersByStatusUseCase = getOrdersByStatus;
        this.getOrderByIdUseCase = getOrderById;
    }

    public ResponseEntity<?> createOrder (OrderRequest orderRequest, String authorizationHeader) {

        Order order = createOrderUseCase.createOrder(orderRequest, authorizationHeader);
        OrderResponse response = OrderPresenter.mapOrderToResponse(order);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<List<OrderResponse>> getAllOrders( String authorizationHeader) {
        List<Order> orderEntities = getAllOrdersUseCase.getAllOrders(authorizationHeader);

        List<OrderResponse> responses = orderEntities.stream()
                .map(OrderPresenter::mapOrderToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }


    public ResponseEntity<List<OrderResponse>> getOrdersByStatus(StatusOrder status, String authorizationHeader) {
        List<Order> orders = ordersByStatusUseCase.getOrdersByStatus(status, authorizationHeader);

        List<OrderResponse> responses = orders.stream()
                .map(OrderPresenter::mapOrderToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    public  ResponseEntity<OrderResponse> getOrderById(int id, String authorizationHeader) {
        Order order = getOrderByIdUseCase.getOrderById(id, authorizationHeader);
        OrderResponse response = OrderPresenter.mapOrderToResponse(order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public ResponseEntity<?> updateOrderStatus(int id, StatusOrder newStatus, String authorizationHeader) {

        Order order = getOrderByIdUseCase.getOrderById(id, authorizationHeader);

        ordersByStatusUseCase.updateOrderStatus(order, newStatus, authorizationHeader);
        OrderResponse response = OrderPresenter.mapOrderToResponse(order);
        return ResponseEntity.ok(response);
    }



}
