package com.fiap.burguer.infraestructure.adapters;

import com.fiap.burguer.infraestructure.entities.CheckOutEntity;
import com.fiap.burguer.infraestructure.repository.CheckoutRepository;
import com.fiap.burguer.core.application.ports.CheckOutPort;
import com.fiap.burguer.core.domain.CheckOut;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CheckOutAdapter implements CheckOutPort {
    @Autowired
    CheckoutRepository checkoutRepository;

    private ModelMapper modelMapper;

    @Override
    public CheckOut findById(int id) {
        CheckOutEntity checkOutEntityResponse = checkoutRepository.findById(id);
        return modelMapper.map(checkOutEntityResponse, CheckOut.class);
    }

    @Override
    public CheckOut save(CheckOut checkOut) {
        CheckOutEntity checkOutEntity = modelMapper.map(checkOut, CheckOutEntity.class);
        CheckOutEntity checkOutEntityResponse = checkoutRepository.save(checkOutEntity);
        return modelMapper.map(checkOutEntityResponse, CheckOut.class);
    }
}

