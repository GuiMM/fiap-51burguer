package com.fiap.burguer.api;

import com.fiap.burguer.driver.dto.ClientCreate;
import com.fiap.burguer.driver.dto.ErrorResponse;
import com.fiap.burguer.core.domain.Client;
import com.fiap.burguer.infraestructure.entities.ClientEntity;
import com.fiap.burguer.infraestructure.entities.ProductEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/client")
@RestController
public interface ClientApi {

    @PostMapping(name = "/create", produces = "application/json")
    @Operation(summary = "Cadastra cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente cadastrado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class)) }),
            @ApiResponse(responseCode = "400", description = "Infos de cliente inválido",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }) })
    public @ResponseBody ResponseEntity<?> postClient(@Valid ClientCreate clientCreate);

    @GetMapping("/{id}")
    @Operation(summary = "Consulta cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrou cliente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientEntity.class)) }),
            @ApiResponse(responseCode = "400", description = "Id de cliente inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrada",
                    content = @Content) })
    public @ResponseBody ResponseEntity<Client> getClientById(
            @Parameter(description = "ID do cliente a ser consultada", required = true) @PathVariable("id") int id);

    @GetMapping("/cpf")
    @Operation(summary = "Consulta cliente por CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrou cliente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientEntity.class)) }),
            @ApiResponse(responseCode = "400", description = "Id de cliente inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrada",
                    content = @Content) })
    public @ResponseBody ResponseEntity<Client> getClientByCpf(
            @Parameter(description = "CPF do cliente a ser consultada", required = true) @RequestParam String cpf);

    @PutMapping(name = "/update", produces = "application/json")
    @Operation(summary = "Atualiza cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductEntity.class)) }),
            @ApiResponse(responseCode = "400", description = "Infos de cliente invalido",
                    content = @Content)})
    public @ResponseBody Client putClient(@Valid Client client);

    @DeleteMapping("{id}")
    @Operation(summary = "Deleta cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletou cliente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientEntity.class)) }),
            @ApiResponse(responseCode = "400", description = "Id de cliente inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                    content = @Content) })
    public @ResponseBody ResponseEntity deleteClient(
            @Parameter(description = "ID do cliente a ser deletado", required = true) @PathVariable("id") int id);
}
