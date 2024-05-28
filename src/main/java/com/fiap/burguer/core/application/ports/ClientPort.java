package com.fiap.burguer.core.application.ports;

import com.fiap.burguer.core.domain.Client;

public interface ClientPort {
    Client save(Client client);
    Client findById(int id);
    Client findByCpf(String cpf);
    void deleteById(int id);
}
