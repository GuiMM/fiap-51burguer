package com.fiap.burguer.infraestructure.adapters;

import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.ports.IPaymentGateway;
import org.springframework.stereotype.Component;

@Component
public class PaymentGateway implements IPaymentGateway {

    @Override
    public boolean processPayment(StatusOrder statusOrder) {
        return statusOrder == StatusOrder.APPROVEDPAYMENT;
    }
}
