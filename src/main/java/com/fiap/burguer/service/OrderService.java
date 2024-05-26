package com.fiap.burguer.service;

import com.fiap.burguer.dto.OrderRequest;
import com.fiap.burguer.dto.OrderResponse;
import com.fiap.burguer.entities.CheckOut;
import com.fiap.burguer.entities.Order;
import com.fiap.burguer.entities.OrderItem;
import com.fiap.burguer.entities.Product;
import com.fiap.burguer.enums.StatusOrder;
import com.fiap.burguer.repository.CheckoutRepository;
import com.fiap.burguer.repository.OrderRepository;
import com.fiap.burguer.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CheckoutRepository checkoutRepository;

    public OrderService(OrderRepository orderRepository) {
    }

    public OrderResponse mapOrderToResponse(Order order) {
        if (order == null) {
            return null;
        }

        double totalPrice = 0.0;
        Integer timeWaitingOrder = 0;
        Date date = new Date();
        List<Product> products = new ArrayList<>();

        for (OrderItem item : order.getOrderItemsList()) {
            totalPrice += item.getProductPrice() * item.getAmount();
            Integer preparationTime = Integer.parseInt(item.getPreparationTime());
            timeWaitingOrder += (preparationTime * item.getAmount());
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
            Optional<Product> optionalProduct = Optional.ofNullable(productRepository.findById(itemRequest.getProductId()));
            Product product = optionalProduct.orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setAmount(itemRequest.getQuantity());
            orderItem.setProductPrice(product.getPrice());
            orderItem.setPreparationTime(product.getPreparationTime().toString());
            orderItem.setDescription(product.getDescription());
            orderItem.setOrder(order);

            order.setTotalPrice(order.getTotalPrice() + (product.getPrice() * itemRequest.getQuantity()));
            timeOrder.updateAndGet(v -> v + order.getTimeWaitingOrder() + (product.getPreparationTime() * itemRequest.getQuantity()));


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
