package com.fiap.burguer.driver.controller;
import com.fiap.burguer.api.ClientApi;
import com.fiap.burguer.driver.dto.ClientCreate;
import com.fiap.burguer.core.application.usecases.ClientUseCases;
import com.fiap.burguer.core.domain.Client;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class ClientController implements ClientApi {
    private final ClientUseCases clientUseCases;

    public ClientController(ClientUseCases clientUseCases) {
        this.clientUseCases = clientUseCases;
    }


    public ResponseEntity<?> postClient(ClientCreate clientCreate) {
        Client entity = new Client();
        entity.setName(clientCreate.getNameClient());
        entity.setEmail(clientCreate.getEmailClient());
        entity.setCpf(clientCreate.getCpfClient());

       Client newClient = clientUseCases.saveClientOrUpdate(entity);
       return ResponseEntity.ok().body(newClient);
    }

    public  ResponseEntity<Client> getClientById(int id) {
        Client client = clientUseCases.findById(id);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    public ResponseEntity<Client> getClientByCpf(String cpf) {
        Client client = clientUseCases.findByCpf(cpf);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    public Client putClient(Client client) {
        return clientUseCases.saveClientOrUpdate(client);
    }

    public  ResponseEntity deleteClient(int id) {
            clientUseCases.deleteById(id);
            return new ResponseEntity<>("Cliente Deletado com sucesso",HttpStatus.OK);
    }
}
