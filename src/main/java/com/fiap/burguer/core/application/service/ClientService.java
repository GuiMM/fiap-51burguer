package com.fiap.burguer.core.application.service;

import com.fiap.burguer.adapter.driver.handlers.InvalidCPFException;
import com.fiap.burguer.adapter.driver.handlers.InvalidEmailException;
import com.fiap.burguer.core.application.ports.ClientPort;
import com.fiap.burguer.core.application.utils.CPFUtils;
import com.fiap.burguer.core.application.utils.EmailUtils;
import com.fiap.burguer.core.domain.Client;

public class ClientService {
    private final ClientPort clientPort;

    public ClientService(ClientPort clientPort) {
        this.clientPort = clientPort;
    }

    public Client saveClientOrUpdate(Client client) {
        client.setCpf(client.getCpf().replaceAll("\\D", ""));
        if (!CPFUtils.isValidCPF(client.getCpf())) {
            throw new InvalidCPFException("CPF '" + client.getCpf() + "' inválido!");
        }

        if (!EmailUtils.isValidEmail(client.getEmail())) {
            throw new InvalidEmailException("E-mail '" + client.getEmail() + "' inválido!");
        }

        return clientPort.save(client);
    }

    public Client findById(int id) {
        return clientPort.findById(id);
    }

    public Client findByCpf(String cpf) {
        return clientPort.findByCpf(cpf);
    }

    public void deleteById(int id) {
        clientPort.deleteById(id);
    }
}
