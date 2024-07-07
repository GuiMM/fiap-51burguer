package com.fiap.burguer.core.application.usecases;
import com.fiap.burguer.core.application.Exception.ResourceNotFoundException;
import com.fiap.burguer.core.application.ports.CheckOutPort;
import com.fiap.burguer.core.application.ports.IPaymentGateway;
import com.fiap.burguer.core.application.Exception.ImpossibleToCheckoutException;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.ports.OrderPort;
import com.fiap.burguer.core.domain.CheckOut;
import com.fiap.burguer.core.domain.Order;

import java.util.Date;

public class CheckoutUseCases {
    private final CheckOutPort checkOutPort;
    private final OrderPort orderPort;
    private final IPaymentGateway paymentGateway;

    public CheckoutUseCases(CheckOutPort checkOutPort, OrderPort orderPort, IPaymentGateway paymentGateway) {
        this.checkOutPort = checkOutPort;
        this.orderPort = orderPort;
        this.paymentGateway = paymentGateway;
    }

    public CheckOut findById(int id) {
        CheckOut checkout = checkOutPort.findById(id);
        if(checkout == null) throw new ResourceNotFoundException("Checkout não encontrado.");
        return checkout;
    }


    public void updateStatusOrder(Order order, StatusOrder statusOrder) {
        if (paymentGateway.processPayment(statusOrder)) {
            order.setStatus(StatusOrder.RECEIVED);

        } else {
            order.setStatus(StatusOrder.WAITINGPAYMENT);

        }
        orderPort.save(order);

    }

    public CheckOut save(CheckOut checkOut) {
        return checkOutPort.save(checkOut);
    }


    public CheckOut createCheckout(int id, StatusOrder statusOrder){
        Order order = orderPort.findById(id);
        if(order.getStatus() == StatusOrder.RECEIVED|| order.getStatus() == StatusOrder.CANCELED){
            String errorMessage = "Não foi possível realizar o pagamento, pois o pedido " + order.getId() + " já está com o status "+ order.getStatus() ;
            throw new ImpossibleToCheckoutException(errorMessage);
        }else{
            this.updateStatusOrder(order, statusOrder);
        }
        CheckOut checkoutNew = this.save(this.mapOrderToCheckout(order));

        if (checkoutNew == null) {
            throw new RuntimeException("Não foi possível processar a solicitação");
        }
        return checkoutNew;
    }

    public CheckOut mapOrderToCheckout(Order order) {
        CheckOut checkoutNew = new CheckOut();
        checkoutNew.setDateCreated(new Date());
        checkoutNew.setOrder(order);
        checkoutNew.setTotalPrice(order.getTotalPrice());
        checkoutNew.setTransactId(checkoutNew.getTransactId());
        checkoutNew.setPaymentStatus(StatusOrder.APPROVEDPAYMENT);
        return checkoutNew;
    }

}

