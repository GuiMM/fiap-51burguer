package com.fiap.burguer.adapters;

import com.fiap.burguer.enums.StatusOrder;
import com.fiap.burguer.ports.IPaymentGateway;
import org.springframework.stereotype.Component;

@Component
public class PaymentGateway implements IPaymentGateway {

    @Override
    public boolean processPayment(StatusOrder statusOrder) {
        return statusOrder == StatusOrder.APPROVEDPAYMENT;
    }
}
