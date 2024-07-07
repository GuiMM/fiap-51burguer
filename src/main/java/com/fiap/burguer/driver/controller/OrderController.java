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
        try {

            if (orderRequest.getItems() != null) {
                for (OrderRequest.OrderItemRequest item : orderRequest.getItems()) {
                    if (orderRequest.getIdClient() != null) {

                        Client client = clientservice.findById(orderRequest.getIdClient());
                        if(client == null)
                            throw new IllegalArgumentException("Cliente não encontrado");

                        orderRequest.setClient(client);

                    }

                    if (item.getQuantity() <= 0) {
                        String errorMessage = "A quantidade do item com ID " + item.getProductId() + " deve ser maior que zero.";
                        return ResponseEntity.badRequest().body(errorMessage);
                    }
                }
            }

            Order order = orderUseCases.createOrder(orderRequest);
            OrderResponse response = OrderPresenter.mapOrderToResponse(order);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<Order> orderEntities = orderUseCases.getAllOrders();
        if (orderEntities == null || orderEntities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<OrderResponse> responses = orderEntities.stream()
                .map(OrderPresenter::mapOrderToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }


    public ResponseEntity<List<OrderResponse>> getOrdersByStatus(StatusOrder status) {
        List<Order> orders = orderUseCases.getOrdersByStatus(status);
        if (orders == null || orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<OrderResponse> responses = orders.stream()
                .map(OrderPresenter::mapOrderToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    public  ResponseEntity<OrderResponse> getOrderById(int id) {
        try {
            Order order = orderUseCases.getOrderById(id);
            if (order == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            OrderResponse response = OrderPresenter.mapOrderToResponse(order);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> updateOrderStatus(int id, StatusOrder newStatus) {
        try {
            Order order = orderUseCases.getOrderById(id);
            if (order == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }


            boolean isValidUpdate = isValidStatusUpdate(order.getStatus(), newStatus);
            if (!isValidUpdate) {
                return ResponseEntity.badRequest().body("Atualização de status para: " + newStatus + " inválida");
            }


            order.setStatus(newStatus);
            orderUseCases.updateOrderStatus(order);
            OrderResponse response = OrderPresenter.mapOrderToResponse(order);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isValidStatusUpdate(StatusOrder currentStatus, StatusOrder newStatus) {
        if (!isValidNextStatus(currentStatus, newStatus)) {
            return false;
        }

        if (newStatus == StatusOrder.CANCELED) {
            return isCancelValid(currentStatus);
        }

        if (newStatus == StatusOrder.RECEIVED && currentStatus != StatusOrder.APPROVEDPAYMENT) {
            return false;
        }

        return true;
    }

    private boolean isValidNextStatus(StatusOrder currentStatus, StatusOrder newStatus) {
        return switch (currentStatus) {
            case WAITINGPAYMENT ->
                    newStatus == StatusOrder.APPROVEDPAYMENT || newStatus == StatusOrder.REJECTEDPAYMENT || newStatus == StatusOrder.CANCELED;
            case RECEIVED -> newStatus == StatusOrder.PREPARATION;
            case PREPARATION -> newStatus == StatusOrder.READY;
            case READY -> newStatus == StatusOrder.FINISHED;
            case REJECTEDPAYMENT -> newStatus == StatusOrder.CANCELED;
            default -> false;
        };
    }

    private boolean isCancelValid(StatusOrder currentStatus) {
        return currentStatus == StatusOrder.WAITINGPAYMENT || currentStatus == StatusOrder.REJECTEDPAYMENT;
    }

}
