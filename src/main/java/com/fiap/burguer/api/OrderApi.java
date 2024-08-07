package com.fiap.burguer.api;

import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.usecases.ClientUseCases;
import com.fiap.burguer.core.application.usecases.OrderUseCases;
import com.fiap.burguer.core.domain.Client;
import com.fiap.burguer.core.domain.Order;
import com.fiap.burguer.driver.dto.OrderRequest;
import com.fiap.burguer.driver.dto.OrderResponse;
import com.fiap.burguer.driver.presenters.OrderPresenter;
import com.fiap.burguer.infraestructure.entities.OrderEntity;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public interface OrderApi {

    @PostMapping
    @Operation(summary = "Cria um novo pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content)})
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderRequest orderRequest);

    @GetMapping
    @Operation(summary = "Consulta todos os pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrou pedidos",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Nenhum pedido encontrado",
                    content = @Content)})
    public ResponseEntity<List<OrderResponse>> getAllOrders();

    @GetMapping("/status/{status}")
    @Operation(summary = "Consulta pedidos por status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrou pedidos",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Status inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Nenhum pedido encontrado com o status informado",
                    content = @Content)})
    public ResponseEntity<List<OrderResponse>> getOrdersByStatus(
            @Parameter(description = "Status do pedido", required = true)
            @PathVariable("status") StatusOrder status);

    @GetMapping("/{id}")
    @Operation(summary = "Consulta pedido por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrou pedido",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Id de pedido inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content)})
    public @ResponseBody ResponseEntity<OrderResponse> getOrderById(
            @Parameter(description = "ID do pedido a ser consultado", required = true) @PathVariable("id") int id);

    @PutMapping("/{id}/status")
    @Operation(summary = "Atualiza o status de um pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status do pedido atualizado com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content)})
    public ResponseEntity<?> updateOrderStatus(
            @Parameter(description = "ID do pedido a ser atualizado", required = true)
            @PathVariable("id") int id,
            @Parameter(description = "Novo status do pedido", required = true, schema = @Schema(allowableValues = {
                    "PREPARATION",
                    "READY",
                    "FINISHED",
                    "CANCELED"}))
            @RequestParam StatusOrder newStatus);

}
