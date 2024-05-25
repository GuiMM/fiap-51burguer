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
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
        //Método para chamar a função e iniciar o FakeChekout
        initCheckout(order);
        return orderRepository.save(order);
    }
//Esse método é para iniciar o checkout assim que o pedido for criado
    public void initCheckout(Order order){
        CheckOut checkOut = new  CheckOut(); // Crio um objeto checkout
        checkOut.setTransactId(generateTransactId()); // defino o transactId
        checkOut.setPayment_status(StatusOrder.PREPARATION);// altero o status para preparando
        checkOut.setDateCreated(new Date());// Defino a hora que o checkout foi criado
        checkOut.setOrder(order.getId()); // Passo o ID do pedido (conferir depois se é esse método que traz o valor ou se precisa consultar do banco)
        checkoutRepository.saveCheckOut(checkOut);// chamado o método do repository para salvar o checkout

    }
// Método para gerar um TransactId, como estamos fazendo algo fake
// Fiz a seguinte validação, a cade 20 pedido inseridos com os últimos dígitos 1, ele gerará um com dígito para compra negada
String generateTransactId() {
    long count = checkoutRepository.countCheckOut(); // consulta quantos checkouts existem no banco
    String baseId = UUID.randomUUID().toString().replace("-", ""); // gera a base do id Transact
    if (baseId.length() < 30) {
        baseId = baseId + UUID.randomUUID().toString().replace("-", "").substring(0, 30 - baseId.length());
    }
    String suffix;
    if ((count + 1) % 15 == 0) {
        suffix = "000";
    } else {
        suffix = String.format("%03d", ((int)(Math.random() * 900) + 100));
        suffix = suffix.substring(0, 2) + "1";
    }
    return baseId.substring(0, baseId.length() - 3) + suffix;
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
