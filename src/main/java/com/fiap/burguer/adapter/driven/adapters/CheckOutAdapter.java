package com.fiap.burguer.adapter.driven.adapters;

import com.fiap.burguer.adapter.driven.entities.CheckOutEntity;
import com.fiap.burguer.adapter.driven.entities.ClientEntity;
import com.fiap.burguer.adapter.driven.mappers.CheckOutMapper;
import com.fiap.burguer.adapter.driven.mappers.ClientMapper;
import com.fiap.burguer.adapter.driven.repository.CheckoutRepository;
import com.fiap.burguer.core.application.ports.CheckOutPort;
import com.fiap.burguer.core.domain.CheckOut;
import org.springframework.beans.factory.annotation.Autowired;

public class CheckOutAdapter implements CheckOutPort {
    @Autowired
    CheckoutRepository checkoutRepository;

    @Override
    public CheckOut findById(int id) {
        CheckOutEntity checkOutEntityResponse =  checkoutRepository.findById(id);
        return CheckOutMapper.toDomain(checkOutEntityResponse);
    }

    @Override
    public CheckOut save(CheckOut checkOut) {
        CheckOutEntity checkOutEntity = CheckOutMapper.toEntity(checkOut);
        CheckOutEntity checkOutEntityResponse =  checkoutRepository.save(checkOutEntity);
        return CheckOutMapper.toDomain(checkOutEntityResponse);
    }
    }
}
