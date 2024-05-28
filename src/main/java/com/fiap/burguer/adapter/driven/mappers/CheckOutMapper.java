package com.fiap.burguer.adapter.driven.mappers;

import com.fiap.burguer.adapter.driven.entities.CheckOutEntity;
import com.fiap.burguer.adapter.driven.entities.ClientEntity;
import com.fiap.burguer.core.domain.CheckOut;
import com.fiap.burguer.core.domain.Client;
import org.springframework.beans.BeanUtils;

public class CheckOutMapper {
    public static CheckOutEntity toEntity(CheckOut checkOut) {
        CheckOutEntity checkoutEntity = new CheckOutEntity();
        BeanUtils.copyProperties(checkOut, checkoutEntity);
        return checkoutEntity;
    }

    public static CheckOut toDomain(CheckOutEntity checkoutEntity) {
        CheckOut checkOut = new CheckOut();
        BeanUtils.copyProperties(checkoutEntity, checkOut);
        return checkOut;
    }
}
