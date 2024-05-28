package com.fiap.burguer.core.application.service;

import com.fiap.burguer.adapter.driven.entities.OrderEntity;
import com.fiap.burguer.adapter.driven.entities.OrderItemEntity;
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

//    public OrderEntity findOrderById(int id) {
//        return orderRepository.getById(id);
//    }

    public void updateStatusOrder(OrderEntity orderEntity, StatusOrder statusOrder) {
        if (paymentGateway.processPayment(statusOrder)) {
            orderEntity.setStatus(StatusOrder.RECEIVED);

        } else {
            orderEntity.setStatus(StatusOrder.WAITINGPAYMENT);

        }
        orderRepository.save(orderEntity);

    }

    public CheckOut save(CheckOut checkOut) {
        return checkoutRepository.save(checkOut);
    }

    public CheckOut mapOrderToCheckout(OrderEntity orderEntity, StatusOrder statusOrder) {
        CheckOut checkoutNew = new CheckOut();
        checkoutNew.setDateCreated(new Date());
        checkoutNew.setOrderEntity(orderEntity);
        checkoutNew.setTotalPrice(orderEntity.getTotalPrice());
        checkoutNew.setTransactId(checkoutNew.getTransactId());
        checkoutNew.setPaymentStatus(StatusOrder.APPROVEDPAYMENT);
        return checkoutNew;
    }

//    public CheckoutResponse mapCheckoutToResponse(CheckOut checkOut) {
//        if (checkOut == null) {
//            return null;
//        }
//        double totalPrice = 0.0;
//        Integer timeWaitingOrder = 0;
//        Date date = new Date();
//        List<ProductEntity> productEntities = new ArrayList<>();
//
//        for (OrderItemEntity item : checkOut.getOrderEntity().getOrderItemsListEntity()) {
//            totalPrice += item.getProductPrice() * item.getAmount();
//            Integer preparationTime = Integer.parseInt(item.getPreparationTime());
//            timeWaitingOrder += (preparationTime * item.getAmount());
//            date = item.getOrderEntity().getDateCreated();
//            productEntities.add(item.getProductEntity());
//        }
//        OrderResponse responseOrder = new OrderResponse();
//        responseOrder.setId(checkOut.getOrderEntity().getId());
//        responseOrder.setStatus(checkOut.getOrderEntity().getStatus() != null ? checkOut.getOrderEntity().getStatus().toString() : null);
//        responseOrder.setTotalPrice(checkOut.getOrderEntity().getTotalPrice());
//        responseOrder.setTimeWaitingOrder(checkOut.getOrderEntity().getTimeWaitingOrder());
//        responseOrder.setDateCreated(date);
//        responseOrder.setProducts(productEntities);
//
//        if (checkOut.getOrderEntity().getClientEntity() != null) {
//            responseOrder.setClient(checkOut.getOrderEntity().getClientEntity());
//        }
//
//        CheckoutResponse responseCheckout = new CheckoutResponse();
//        responseCheckout.setOrder(responseOrder);
//        responseCheckout.setTransactId(checkOut.getTransactId());
//        responseCheckout.setId(checkOut.getId());
//        responseCheckout.setPayment_status(checkOut.getPaymentStatus());
//        responseCheckout.setDateCreated(checkOut.getDateCreated());
//        responseCheckout.setTotalPrice(checkOut.getTotalPrice());
//        return responseCheckout;
//    }
}

