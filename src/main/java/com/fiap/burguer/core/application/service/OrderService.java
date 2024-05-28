package com.fiap.burguer.core.application.service;

import com.fiap.burguer.adapter.driven.adapters.OrderAdapter;
import com.fiap.burguer.adapter.driven.adapters.ProductAdapter;
import com.fiap.burguer.core.application.dto.OrderRequest;
import com.fiap.burguer.core.application.dto.OrderResponse;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.domain.Order;
import com.fiap.burguer.core.domain.OrderItem;
import com.fiap.burguer.core.domain.Product;


import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class OrderService {
    private final OrderAdapter orderAdapter;
    private final ProductAdapter productAdapter;

    public OrderService(OrderAdapter orderAdapter, ProductAdapter productAdapter) {
        this.orderAdapter = orderAdapter;
        this.productAdapter = productAdapter;
    }

    public OrderResponse mapOrderToResponse(Order order) {
        if (order == null) {
            return null;
        }

        Date date = new Date();
        List<Product> products = new ArrayList<>();

        if(order.getOrderItemsList() != null)
            for (OrderItem item : order.getOrderItemsList()) {
                Integer preparationTime = Integer.parseInt(item.getPreparationTime());
                date = item.getOrder().getDateCreated();
                products.add(item.getProduct());
            }

        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setStatus(order.getStatus() != null ? order.getStatus().toString() : null);
        response.setTotalPrice(order.getTotalPrice());
        response.setTimeWaitingOrder(order.getTimeWaitingOrder());
        response.setDateCreated(date);
        response.setProducts(products);

        if (order.getClient() != null) {
            response.setClient(order.getClient());
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

        if (orderRequest.getClient() != null) {
            order.setClient(orderRequest.getClient());
        }

        List<OrderItem> orderItems = orderRequest.getItems().stream().map(itemRequest -> {
            Optional<Product> optionalProduct = Optional.ofNullable(productAdapter.findById(itemRequest.getProductId()));
            Product product = optionalProduct.orElseThrow(() -> new RuntimeException("Product not found"));

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
        return orderAdapter.save(order);
    }

    public Order getOrderById(int id) {
        return orderAdapter.findById(id);
    }

    public List<Order> getAllOrders() {
        return orderAdapter.findAll();
    }

    public List<Order> getOrdersByStatus(StatusOrder status) {
        return orderAdapter.findByStatus(status);
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
        orderAdapter.save(order);
    }
}
