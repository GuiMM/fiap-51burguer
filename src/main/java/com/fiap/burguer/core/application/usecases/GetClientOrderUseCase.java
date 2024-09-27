package com.fiap.burguer.core.application.usecases;

import com.fiap.burguer.core.application.ports.ClientPort;
import com.fiap.burguer.core.domain.Client;
import com.fiap.burguer.driver.dto.OrderRequest;

public class GetClientOrderUseCase {
    private final ClientPort clientPort;

    public GetClientOrderUseCase(ClientPort clientPort) {
        this.clientPort = clientPort;
    }

    public Client execute(OrderRequest orderRequest) {
        if (orderRequest.getIdClient() != null) {
            Client client = clientPort.findById(orderRequest.getIdClient());
            if (client == null) {
                throw new IllegalArgumentException("Cliente não encontrado");
            }
            return client;
        }
        return null;
    }
}

