package com.fiap.burguer.core.application.ports;

import com.fiap.burguer.core.application.enums.StatusOrder;

public interface IPaymentGateway {

    boolean processPayment(StatusOrder statusOrder);
}
