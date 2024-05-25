package com.fiap.burguer.repository;

import com.fiap.burguer.entities.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {
    Client save(Client category);
    Client findById(int id);
    Client findByCpf(String cpf);
}
