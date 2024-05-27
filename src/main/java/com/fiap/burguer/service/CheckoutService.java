package com.fiap.burguer.service;
import com.fiap.burguer.dto.CheckoutResponse;
import com.fiap.burguer.dto.OrderResponse;
import com.fiap.burguer.entities.*;
import com.fiap.burguer.enums.StatusOrder;
import com.fiap.burguer.ports.IPaymentGateway;
import com.fiap.burguer.repository.CheckoutRepository;
import com.fiap.burguer.repository.OrderRepository;

// Essa classe, é para realizar uma requisição consultará o checkout, através do ID do pedido
import com.fiap.burguer.entities.CheckOut;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CheckoutService  {
    private final CheckoutRepository checkoutRepository;
    private final OrderRepository orderRepository;
    private final IPaymentGateway paymentGateway;

    public CheckoutService(CheckoutRepository checkoutRepository,  OrderRepository orderRepository, IPaymentGateway paymentGateway) {
        this.checkoutRepository = checkoutRepository;
        this.orderRepository = orderRepository;
        this.paymentGateway = paymentGateway;
    }

    public CheckOut findById(int id) {
        return checkoutRepository.findById(id);
    }
    public Order findOrderById(int id){
        return orderRepository.getById(id);
    }

    public void updateStatusOrder(Order order, StatusOrder statusOrder){
        if(paymentGateway.processPayment(statusOrder)){
            order.setStatus(StatusOrder.RECEIVED);

        }else{
            order.setStatus(StatusOrder.WAITINGPAYMENT);

        }
        orderRepository.save(order);

    }

    public CheckOut save(CheckOut checkOut) {
       return  checkoutRepository.save(checkOut);
    }

    public CheckOut mapOrderToCheckout(Order order, StatusOrder statusOrder){
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
        responseCheckout.setPayment_status(checkOut.getPaymentStatus());
        responseCheckout.setDateCreated(checkOut.getDateCreated());
        responseCheckout.setTotalPrice(checkOut.getTotalPrice());
        return responseCheckout;
    }
}

