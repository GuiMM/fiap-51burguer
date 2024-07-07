package com.fiap.burguer.infraestructure.mappers;

import com.fiap.burguer.infraestructure.entities.ClientEntity;
import com.fiap.burguer.core.domain.Client;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public static ClientEntity toEntity(Client client) {
        if(client == null) return null;
        ClientEntity clientEntity = new ClientEntity();
        BeanUtils.copyProperties(client, clientEntity);
        return clientEntity;
    }

    public static Client toDomain(ClientEntity clientEntity) {
        if(clientEntity == null) return null;
        Client client = new Client();
        BeanUtils.copyProperties(clientEntity, client);
        return client;
    }
}
