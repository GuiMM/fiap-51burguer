package com.fiap.burguer.service;
import com.fiap.burguer.dto.CheckoutResponse;
import com.fiap.burguer.dto.OrderResponse;
import com.fiap.burguer.entities.*;
import com.fiap.burguer.enums.StatusOrder;
import com.fiap.burguer.repository.CheckoutRepository;
import com.fiap.burguer.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Essa classe, é para realizar uma requisição consultará o checkout, através do ID do pedido
import com.fiap.burguer.entities.CheckOut;
import com.fiap.burguer.repository.CheckoutRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CheckoutService {
    private final CheckoutRepository checkoutRepository;
    @Autowired
    private  OrderRepository orderRepository;


    public CheckoutService(CheckoutRepository checkoutRepository) {
        this.checkoutRepository = checkoutRepository;
    }

    public CheckOut findById(int id) {
        return checkoutRepository.findById(id);
    }
    public Order findOrderById(int id){
        return orderRepository.getById(id);
    }

    public Order updateStatusOrder(Order order, StatusOrder statusOrder){
        if(statusOrder == StatusOrder.APPROVEDPAYMENT){
            order.setStatus(StatusOrder.RECEIVED);

        }else{
            order.setStatus(StatusOrder.WAITINGPAYMENT);

        }
        return orderRepository.save(order);

    }

    public CheckOut save(CheckOut checkOut) {
       return  checkoutRepository.save(checkOut);
    }

    public CheckOut mapOrderToCheckout(Order order, StatusOrder status_order){
        CheckOut checkoutNew = new CheckOut();
        checkoutNew.setDateCreated(new Date());
        checkoutNew.setOrder(order);
        checkoutNew.setTotalPrice(order.getTotalPrice());
        checkoutNew.setTransactId(checkoutNew.getTransactId());
        checkoutNew.setPayment_status(status_order);
        return checkoutNew;
    }

    public CheckoutResponse mapCheckoutToResponse(CheckOut checkOut) {
        if (checkOut == null) {
            return null;
        }
        double totalPrice = 0.0;
        Integer timeWaitingOrder = 0;
        Date date = new Date();
        List<Product> products = new ArrayList<>();

        for (OrderItem item : checkOut.getOrder().getOrderItemsList()) {
            totalPrice += item.getProductPrice() * item.getAmount();
            Integer preparationTime = Integer.parseInt(item.getPreparationTime());
            timeWaitingOrder += (preparationTime * item.getAmount());
            date = item.getOrder().getDateCreated();
            products.add(item.getProduct());
        }
        OrderResponse responseOrder = new OrderResponse();
        responseOrder.setId(checkOut.getOrder().getId());
        responseOrder.setStatus(checkOut.getOrder().getStatus() != null ? checkOut.getOrder().getStatus().toString() : null);
        responseOrder.setTotalPrice(checkOut.getOrder().getTotalPrice());
        responseOrder.setTimeWaitingOrder(checkOut.getOrder().getTimeWaitingOrder());
        responseOrder.setDateCreated(date);
        responseOrder.setProducts(products);

        if (checkOut.getOrder().getClient() != null) {
            responseOrder.setClient(checkOut.getOrder().getClient());
        }

        CheckoutResponse responseCheckout = new CheckoutResponse();
        responseCheckout.setOrder(responseOrder);
        responseCheckout.setTransactId(checkOut.getTransactId());
        responseCheckout.setId(checkOut.getId());
        responseCheckout.setPayment_status(checkOut.getPayment_status());
        responseCheckout.setDateCreated(checkOut.getDateCreated());
        responseCheckout.setTotalPrice(checkOut.getTotalPrice());
        return responseCheckout;
    }
}

