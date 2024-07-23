package com.fiap.burguer.driver.controller;
import com.fiap.burguer.api.OrderApi;
import com.fiap.burguer.driver.presenters.OrderPresenter;
import com.fiap.burguer.driver.dto.OrderRequest;
import com.fiap.burguer.driver.dto.OrderResponse;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.usecases.ClientUseCases;
import com.fiap.burguer.core.application.usecases.OrderUseCases;
import com.fiap.burguer.core.domain.Client;
import com.fiap.burguer.core.domain.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrderController implements OrderApi {
    private final OrderUseCases orderUseCases;
    private final ClientUseCases clientservice;

    public OrderController(OrderUseCases orderUseCases, ClientUseCases clientservice) {
        this.orderUseCases = orderUseCases;
        this.clientservice = clientservice;
    }

    public ResponseEntity<?> createOrder(OrderRequest orderRequest) {

        Order order = orderUseCases.createOrder(orderRequest);
        OrderResponse response = OrderPresenter.mapOrderToResponse(order);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<Order> orderEntities = orderUseCases.getAllOrders();

        List<OrderResponse> responses = orderEntities.stream()
                .map(OrderPresenter::mapOrderToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }


    public ResponseEntity<List<OrderResponse>> getOrdersByStatus(StatusOrder status) {
        List<Order> orders = orderUseCases.getOrdersByStatus(status);

        List<OrderResponse> responses = orders.stream()
                .map(OrderPresenter::mapOrderToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    public  ResponseEntity<OrderResponse> getOrderById(int id) {
        Order order = orderUseCases.getOrderById(id);
        OrderResponse response = OrderPresenter.mapOrderToResponse(order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public ResponseEntity<?> updateOrderStatus(int id, StatusOrder newStatus) {

        Order order = orderUseCases.getOrderById(id);

        orderUseCases.updateOrderStatus(order, newStatus);
        OrderResponse response = OrderPresenter.mapOrderToResponse(order);
        return ResponseEntity.ok(response);
    }



}
