package com.fiap.burguer.adapters;

import com.fiap.burguer.enums.StatusOrder;
import com.fiap.burguer.ports.IPaymentGateway;

public class PaymentGateway implements IPaymentGateway {

    @Override
    public boolean processPayment(StatusOrder statusOrder) {
        if(statusOrder == StatusOrder.APPROVEDPAYMENT){
         return true;
        }else{
         return false;
        }
    }
}
