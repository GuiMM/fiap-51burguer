package com.fiap.burguer.core.application.usecases;

import com.fiap.burguer.core.application.Exception.RequestException;
import com.fiap.burguer.core.application.Exception.ResourceNotFoundException;
import com.fiap.burguer.core.application.ports.ClientPort;
import com.fiap.burguer.core.domain.Client;
import com.fiap.burguer.driver.dto.OrderRequest;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.ports.OrderPort;
import com.fiap.burguer.core.application.ports.ProductPort;
import com.fiap.burguer.core.domain.Order;
import com.fiap.burguer.core.domain.OrderItem;
import com.fiap.burguer.core.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class OrderUseCases {
    private final OrderPort orderPort;
    private final ProductPort productPort;
    private final ClientPort clientPort;

    public OrderUseCases(OrderPort orderPort, ProductPort productPort, ClientPort clientPort) {
        this.orderPort = orderPort;
        this.productPort = productPort;
        this.clientPort = clientPort;
    }

    public void validateOrder(OrderRequest orderRequest){
        if (orderRequest.getItems() != null) {
            for (OrderRequest.OrderItemRequest item : orderRequest.getItems()) {

                if (item.getQuantity() <= 0) {
                    String errorMessage = "A quantidade do item com ID " + item.getProductId() + " deve ser maior que zero.";
                    throw new RequestException(errorMessage);
                }
            }
        }
    }

    public Client getClientOrder(OrderRequest orderRequest){
        if (orderRequest.getIdClient() != null) {

            Client client = clientPort.findById(orderRequest.getIdClient());
            if(client == null)
                throw new IllegalArgumentException("Cliente não encontrado");

            orderRequest.setClient(client);
            return client;
        }
        return null;
    }

    public Order createOrder(OrderRequest orderRequest) {
        this.validateOrder(orderRequest);

        AtomicReference<Integer> timeOrder = new AtomicReference<>(0);
        orderRequest.setClient(this.getClientOrder(orderRequest));

        Order order = new Order();
        order.setDateCreated(new Date());
        order.setStatus(StatusOrder.WAITINGPAYMENT);
        order.setTotalPrice(0.0);
        order.setTimeWaitingOrder(0);

        if (orderRequest.getClient() != null) {
            order.setClient(orderRequest.getClient());
        }

        List<OrderItem> orderItems = makeOrderItemObjects(orderRequest, timeOrder, order);
        order.setTimeWaitingOrder(timeOrder.get() + timeWaitingOrderQueue());
        order.setOrderItemsList(orderItems);
        return orderPort.save(order);
    }

    private List<OrderItem> makeOrderItemObjects(OrderRequest orderRequest, AtomicReference<Integer> timeOrder, Order order) {
        return orderRequest.getItems().stream().map(itemRequest -> {
            Optional<Product> optionalProduct = Optional.ofNullable(productPort.findById(itemRequest.getProductId()));
            Product product = optionalProduct.orElseThrow(() -> new RuntimeException(String.format("Produto %s não encontrado", itemRequest.getProductId())));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setAmount(itemRequest.getQuantity());
            orderItem.setTotalProductPrice(product.getPrice());
            orderItem.setPreparationTime(product.getPreparationTime().toString());
            orderItem.setDescription(product.getDescription());
            orderItem.setOrder(order);

            order.setTotalPrice(order.getTotalPrice() + (product.getPrice() * itemRequest.getQuantity()));
            timeOrder.updateAndGet(v -> v + order.getTimeWaitingOrder() + (product.getPreparationTime() * itemRequest.getQuantity()));


            return orderItem;
        }).collect(Collectors.toList());
    }

    public Order getOrderById(int id) {
        Order order = orderPort.findById(id);
        if (order == null) {
            throw new ResourceNotFoundException("Pedido não encontrado");
        }
        return order;
    }

    public List<Order> getAllOrders() {
        List<Order> orderEntities = orderPort.findAll();
        if (orderEntities == null || orderEntities.isEmpty()) {
            throw new ResourceNotFoundException("Não existem pedidos ainda");
        }
        return orderEntities;
    }

    public List<Order> getOrdersByStatus(StatusOrder status) {
        List<Order> orderEntities = orderPort.findByStatus(status);
        if (orderEntities == null || orderEntities.isEmpty()) {
            throw new ResourceNotFoundException("Não existem pedidos com esse status");
        }
        return orderEntities;
    }

    public int timeWaitingOrderQueue() {

        List<Order> receivedOrderEntities;
        List<Order> preparationOrderEntities;
        try{
            receivedOrderEntities = getOrdersByStatus(StatusOrder.RECEIVED);
            preparationOrderEntities = getOrdersByStatus(StatusOrder.PREPARATION);

            return sumTimeWaitingOrder(receivedOrderEntities) + sumTimeWaitingOrder(preparationOrderEntities);
        }catch (ResourceNotFoundException ex){
            return 0;
        }
    }

    private int sumTimeWaitingOrder(List<Order> orders) {
        int timeWaiting = 0;
        for (Order order : orders) {
            timeWaiting += order.getTimeWaitingOrder();
        }
        return timeWaiting;
    }

    public void updateOrderStatus(Order order, StatusOrder newStatus) {

        boolean isValidUpdate = isValidStatusUpdate(order.getStatus(), newStatus);
        if (!isValidUpdate) {
            throw new RequestException("Atualização de status para: " + newStatus + " inválida");
        }
        order.setStatus(newStatus);
        orderPort.save(order);
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
