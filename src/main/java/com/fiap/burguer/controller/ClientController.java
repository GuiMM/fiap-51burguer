package com.fiap.burguer.controller;

import com.fiap.burguer.entities.Client;
import com.fiap.burguer.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {
    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping(name = "/create", produces = "application/json")
    @Operation(summary = "Cadastra cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente cadastrado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class)) }),
            @ApiResponse(responseCode = "400", description = "Infos de cliente inválido",
                    content = @Content)})
    public @ResponseBody Client postClient(@Valid Client client) {
        return clientService.saveClientOrUpdate(client);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrou cliente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class)) }),
            @ApiResponse(responseCode = "400", description = "Id de cliente inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrada",
                    content = @Content) })
    public @ResponseBody ResponseEntity<Client> getClientById(
            @Parameter(description = "ID do cliente a ser consultada", required = true) @PathVariable("id") int id) {
        Client client = clientService.findById(id);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @GetMapping("/{cpf}")
    @Operation(summary = "Consulta cliente por CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrou cliente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class)) }),
            @ApiResponse(responseCode = "400", description = "Id de cliente inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrada",
                    content = @Content) })
    public @ResponseBody ResponseEntity<Client> getClientByCpf(
            @Parameter(description = "CPF do cliente a ser consultada", required = true) @RequestParam String cpf) {
        Client client = clientService.findByCpf(cpf);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deleta cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletou cliente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class)) }),
            @ApiResponse(responseCode = "400", description = "Id de cliente inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                    content = @Content) })
    public @ResponseBody ResponseEntity deleteClient(
            @Parameter(description = "ID do cliente a ser deletado", required = true) @PathVariable("id") int id) {

        Client client = clientService.findById(id);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            clientService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

    }

}
