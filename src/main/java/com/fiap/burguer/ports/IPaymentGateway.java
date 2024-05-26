package com.fiap.burguer.ports;

import com.fiap.burguer.enums.StatusOrder;

public interface IPaymentGateway {

    boolean processPayment(StatusOrder statusOrder);
}
