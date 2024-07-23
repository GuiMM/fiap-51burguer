package com.fiap.burguer.infraestructure.adapters;

import com.fiap.burguer.infraestructure.entities.ClientEntity;
import com.fiap.burguer.infraestructure.mappers.ClientMapper;
import com.fiap.burguer.infraestructure.repository.ClientRepository;
import com.fiap.burguer.core.application.ports.ClientPort;
import com.fiap.burguer.core.domain.Client;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClientAdapter implements ClientPort {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public Client save(Client client) {
        ClientEntity clientEntity = ClientMapper.toEntity(client);
        ClientEntity clientEntityResponse = clientRepository.save(clientEntity);

        return ClientMapper.toDomain(clientEntityResponse);
    }

    @Override
    public Client findById(int id) {
        ClientEntity clientEntityResponse = clientRepository.findById(id);

        return ClientMapper.toDomain(clientEntityResponse);
    }

    @Override
    public Client findByCpf(String cpf) {
        ClientEntity clientEntityResponse = clientRepository.findByCpf(cpf);

        return ClientMapper.toDomain(clientEntityResponse);
    }

    @Override
    public void deleteById(int id) {
        clientRepository.deleteById(id);
    }
}
