package com.fiap.burguer.core.application.service;

import com.fiap.burguer.adapter.driven.entities.Order;
import com.fiap.burguer.adapter.driven.entities.OrderItem;
import com.fiap.burguer.adapter.driven.entities.ProductEntity;
import com.fiap.burguer.core.application.dto.CheckoutResponse;
import com.fiap.burguer.core.application.dto.OrderResponse;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.ports.IPaymentGateway;
import com.fiap.burguer.adapter.driven.repository.CheckoutRepository;
import com.fiap.burguer.adapter.driven.repository.OrderRepository;
import com.fiap.burguer.adapter.driven.entities.CheckOut;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CheckoutService {
    private final CheckoutRepository checkoutRepository;
    private final OrderRepository orderRepository;
    private final IPaymentGateway paymentGateway;

    public CheckoutService(CheckoutRepository checkoutRepository, OrderRepository orderRepository, IPaymentGateway paymentGateway) {
        this.checkoutRepository = checkoutRepository;
        this.orderRepository = orderRepository;
        this.paymentGateway = paymentGateway;
    }

    public CheckOut findById(int id) {
        return checkoutRepository.findById(id);
    }

    public Order findOrderById(int id) {
        return orderRepository.getById(id);
    }

    public void updateStatusOrder(Order order, StatusOrder statusOrder) {
        if (paymentGateway.processPayment(statusOrder)) {
            order.setStatus(StatusOrder.RECEIVED);

        } else {
            order.setStatus(StatusOrder.WAITINGPAYMENT);

        }
        orderRepository.save(order);

    }

    public CheckOut save(CheckOut checkOut) {
        return checkoutRepository.save(checkOut);
    }

    public CheckOut mapOrderToCheckout(Order order, StatusOrder statusOrder) {
        CheckOut checkoutNew = new CheckOut();
        checkoutNew.setDateCreated(new Date());
        checkoutNew.setOrder(order);
        checkoutNew.setTotalPrice(order.getTotalPrice());
        checkoutNew.setTransactId(checkoutNew.getTransactId());
        checkoutNew.setPaymentStatus(StatusOrder.APPROVEDPAYMENT);
        return checkoutNew;
    }

    public CheckoutResponse mapCheckoutToResponse(CheckOut checkOut) {
        if (checkOut == null) {
            return null;
        }
        double totalPrice = 0.0;
        Integer timeWaitingOrder = 0;
        Date date = new Date();
        List<ProductEntity> productEntities = new ArrayList<>();

        for (OrderItem item : checkOut.getOrder().getOrderItemsList()) {
            totalPrice += item.getProductPrice() * item.getAmount();
            Integer preparationTime = Integer.parseInt(item.getPreparationTime());
            timeWaitingOrder += (preparationTime * item.getAmount());
            date = item.getOrder().getDateCreated();
            productEntities.add(item.getProductEntity());
        }
        OrderResponse responseOrder = new OrderResponse();
        responseOrder.setId(checkOut.getOrder().getId());
        responseOrder.setStatus(checkOut.getOrder().getStatus() != null ? checkOut.getOrder().getStatus().toString() : null);
        responseOrder.setTotalPrice(checkOut.getOrder().getTotalPrice());
        responseOrder.setTimeWaitingOrder(checkOut.getOrder().getTimeWaitingOrder());
        responseOrder.setDateCreated(date);
        responseOrder.setProductEntities(productEntities);

        if (checkOut.getOrder().getClientEntity() != null) {
            responseOrder.setClientEntity(checkOut.getOrder().getClientEntity());
        }

        CheckoutResponse responseCheckout = new CheckoutResponse();
        responseCheckout.setOrder(responseOrder);
        responseCheckout.setTransactId(checkOut.getTransactId());
        responseCheckout.setId(checkOut.getId());
        responseCheckout.setPayment_status(checkOut.getPaymentStatus());
        responseCheckout.setDateCreated(checkOut.getDateCreated());
        responseCheckout.setTotalPrice(checkOut.getTotalPrice());
        return responseCheckout;
    }
}

