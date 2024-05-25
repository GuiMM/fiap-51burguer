package com.fiap.burguer.service;

import com.fiap.burguer.entities.Client;
import com.fiap.burguer.handlers.InvalidCPFException;
import com.fiap.burguer.handlers.InvalidEmailException;
import com.fiap.burguer.repository.ClientRepository;
import com.fiap.burguer.utils.CPFUtils;
import com.fiap.burguer.utils.EmailUtils;

public class ClientService {
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client saveClientOrUpdate(Client client) {

        if (!CPFUtils.isValidCPF(client.getCpf())) {
            throw new InvalidCPFException("CPF '" + client.getCpf() + "' inválido!");
        }

        if (!EmailUtils.isValidEmail(client.getEmail())) {
            throw new InvalidEmailException("E-mail '" + client.getEmail() + "' inválido!");
        }

        return clientRepository.save(client);


    }

    public Client findById(int id) {
        return clientRepository.findById(id);
    }

    public Client findByCpf(String cpf) {
        return clientRepository.findByCpf(cpf);
    }

    public void deleteById(int id) {
        clientRepository.deleteById(id);
    }
}
