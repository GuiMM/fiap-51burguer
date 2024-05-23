package com.fiap.burguer.service;
import com.fiap.burguer.dto.OrderRequest;
import com.fiap.burguer.dto.OrderResponse;
import com.fiap.burguer.entities.Order;
import com.fiap.burguer.entities.OrderItem;
import com.fiap.burguer.entities.Product;
import com.fiap.burguer.enums.StatusOrder;
import com.fiap.burguer.repository.OrderRepository;
import com.fiap.burguer.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository) {
    }

    public OrderResponse mapOrderToResponse(Order order) {
        if (order == null) {
            return null;
        }

        double totalPrice = 0.0;
        Integer timeWaitingOrder = 0;
        List<Product> products = new ArrayList<>();

        for (OrderItem item : order.getOrderItensList()) {
            totalPrice += item.getProductPrice() * item.getAmount();
            Integer preparationTime = Integer.parseInt(item.getPreparationTime());
            timeWaitingOrder += preparationTime * item.getAmount();
            products.add(item.getProduct());
        }

        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setStatus(order.getStatus() != null ? order.getStatus().toString() : null);
        response.setTotalPrice(totalPrice);
        response.setTimeWaitingOrder(timeWaitingOrder);
        response.setProducts(products);

        return response;
    }


    public Order createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setDateCreated(new Date());
        order.setStatus(StatusOrder.RECEIVED);
        order.setTotalPrice(0.0);
        order.setTimeWaitingOrder(0);

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
            order.setTimeWaitingOrder(order.getTimeWaitingOrder() + (product.getPreparationTime()* itemRequest.getQuantity()));
            return orderItem;
        }).collect(Collectors.toList());

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
}
