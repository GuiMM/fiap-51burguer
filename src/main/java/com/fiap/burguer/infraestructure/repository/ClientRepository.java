package com.fiap.burguer.infraestructure.repository;

import com.fiap.burguer.infraestructure.entities.ClientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<ClientEntity, Integer> {
    ClientEntity save(ClientEntity clientEntity);
    ClientEntity findById(int id);
    ClientEntity findByCpf(String cpf);
}
