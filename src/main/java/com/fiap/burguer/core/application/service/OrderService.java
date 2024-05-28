package com.fiap.burguer.core.application.service;

import com.fiap.burguer.core.application.dto.OrderRequest;
import com.fiap.burguer.core.application.dto.OrderResponse;
import com.fiap.burguer.adapter.driven.entities.Order;
import com.fiap.burguer.adapter.driven.entities.OrderItem;
import com.fiap.burguer.adapter.driven.entities.ProductEntity;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.adapter.driven.repository.OrderRepository;
import com.fiap.burguer.adapter.driven.repository.ProductRepository;


import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public OrderResponse mapOrderToResponse(Order order) {
        if (order == null) {
            return null;
        }

        Date date = new Date();
        List<ProductEntity> productEntities = new ArrayList<>();

        for (OrderItem item : order.getOrderItemsList()) {
            Integer preparationTime = Integer.parseInt(item.getPreparationTime());
            date = item.getOrder().getDateCreated();
            productEntities.add(item.getProductEntity());
        }

        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setStatus(order.getStatus() != null ? order.getStatus().toString() : null);
        response.setTotalPrice(order.getTotalPrice());
        response.setTimeWaitingOrder(order.getTimeWaitingOrder());
        response.setDateCreated(date);
        response.setProductEntities(productEntities);

        if (order.getClientEntity() != null) {
            response.setClientEntity(order.getClientEntity());
        }

        return response;
    }

    public Order createOrder(OrderRequest orderRequest) {
        AtomicReference<Integer> timeOrder = new AtomicReference<>(0);
        Order order = new Order();
        order.setDateCreated(new Date());
        order.setStatus(StatusOrder.WAITINGPAYMENT);
        order.setTotalPrice(0.0);
        order.setTimeWaitingOrder(0);

        if (orderRequest.getClientEntity() != null) {
            order.setClientEntity(orderRequest.getClientEntity());
        }

        List<OrderItem> orderItems = orderRequest.getItems().stream().map(itemRequest -> {
            Optional<ProductEntity> optionalProduct = Optional.ofNullable(productRepository.findById(itemRequest.getProductId()));
            ProductEntity productEntity = optionalProduct.orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setProductEntity(productEntity);
            orderItem.setAmount(itemRequest.getQuantity());
            orderItem.setProductPrice(productEntity.getPrice());
            orderItem.setPreparationTime(productEntity.getPreparationTime().toString());
            orderItem.setDescription(productEntity.getDescription());
            orderItem.setOrder(order);

            order.setTotalPrice(order.getTotalPrice() + (productEntity.getPrice() * itemRequest.getQuantity()));
            timeOrder.updateAndGet(v -> v + order.getTimeWaitingOrder() + (productEntity.getPreparationTime() * itemRequest.getQuantity()));


            return orderItem;
        }).collect(Collectors.toList());
        order.setTimeWaitingOrder(timeOrder.get() + timeWaitingOrderQueue());
        order.setOrderItemsList(orderItems);
        return orderRepository.save(order);
    }

    public Order getOrderById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByStatus(StatusOrder status) {
        return orderRepository.findByStatus(status);
    }

    public int timeWaitingOrderQueue() {
        List<Order> receivedOrders = getOrdersByStatus(StatusOrder.RECEIVED);
        List<Order> preparationOrders = getOrdersByStatus(StatusOrder.PREPARATION);

        return sumTimeWaitingOrder(receivedOrders) + sumTimeWaitingOrder(preparationOrders);
    }

    private int sumTimeWaitingOrder(List<Order> orders) {
        int timeWaiting = 0;
        for (Order order : orders) {
            timeWaiting += order.getTimeWaitingOrder();
        }
        return timeWaiting;
    }

    public void updateOrderStatus(Order order) {
        orderRepository.save(order);
    }
}
