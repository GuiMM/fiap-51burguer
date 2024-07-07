package com.fiap.burguer.core.application.usecases;

import com.fiap.burguer.driver.dto.OrderRequest;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.ports.OrderPort;
import com.fiap.burguer.core.application.ports.ProductPort;
import com.fiap.burguer.core.domain.Order;
import com.fiap.burguer.core.domain.OrderItem;
import com.fiap.burguer.core.domain.Product;


import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class OrderUseCases {
    private final OrderPort orderPort;
    private final ProductPort productPort;

    public OrderUseCases(OrderPort orderPort, ProductPort productPort) {
        this.orderPort = orderPort;
        this.productPort = productPort;
    }

    public Order createOrder(OrderRequest orderRequest) {
        AtomicReference<Integer> timeOrder = new AtomicReference<>(0);
        Order order = new Order();
        order.setDateCreated(new Date());
        order.setStatus(StatusOrder.WAITINGPAYMENT);
        order.setTotalPrice(0.0);
        order.setTimeWaitingOrder(0);

        if (orderRequest.getClient() != null) {
            order.setClient(orderRequest.getClient());
        }

        List<OrderItem> orderItems = orderRequest.getItems().stream().map(itemRequest -> {
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
        order.setTimeWaitingOrder(timeOrder.get() + timeWaitingOrderQueue());
        order.setOrderItemsList(orderItems);
        return orderPort.save(order);
    }

    public Order getOrderById(int id) {
        return orderPort.findById(id);
    }

    public List<Order> getAllOrders() {
        return orderPort.findAll();
    }

    public List<Order> getOrdersByStatus(StatusOrder status) {
        return orderPort.findByStatus(status);
    }

    public int timeWaitingOrderQueue() {
        List<Order> receivedOrderEntities = getOrdersByStatus(StatusOrder.RECEIVED);
        List<Order> preparationOrderEntities = getOrdersByStatus(StatusOrder.PREPARATION);

        return sumTimeWaitingOrder(receivedOrderEntities) + sumTimeWaitingOrder(preparationOrderEntities);
    }

    private int sumTimeWaitingOrder(List<Order> orders) {
        int timeWaiting = 0;
        for (Order order : orders) {
            timeWaiting += order.getTimeWaitingOrder();
        }
        return timeWaiting;
    }

    public void updateOrderStatus(Order order) {
        orderPort.save(order);
    }
}
