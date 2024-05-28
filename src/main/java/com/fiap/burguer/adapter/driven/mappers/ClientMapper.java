package com.fiap.burguer.adapter.driven.mappers;

import com.fiap.burguer.adapter.driven.entities.ClientEntity;
import com.fiap.burguer.core.domain.Client;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public static ClientEntity toEntity(Client client) {
        ClientEntity clientEntity = new ClientEntity();
        BeanUtils.copyProperties(client, clientEntity);
        return clientEntity;
    }

    public static Client toDomain(ClientEntity clientEntity) {
        Client client = new Client();
        BeanUtils.copyProperties(clientEntity, client);
        return client;
    }
}
