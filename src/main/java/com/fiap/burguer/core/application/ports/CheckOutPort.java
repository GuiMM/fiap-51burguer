package com.fiap.burguer.core.application.ports;

import com.fiap.burguer.core.domain.CheckOut;

public interface CheckOutPort {
    CheckOut findById(int id);
    CheckOut save(CheckOut checkOut);

}
