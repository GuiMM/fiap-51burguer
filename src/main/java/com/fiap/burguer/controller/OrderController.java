package com.fiap.burguer.controller;

import com.fiap.burguer.dto.OrderRequest;
import com.fiap.burguer.dto.OrderResponse;
import com.fiap.burguer.entities.Client;
import com.fiap.burguer.entities.Order;
import com.fiap.burguer.enums.StatusOrder;
import com.fiap.burguer.repository.ClientRepository;
import com.fiap.burguer.service.OrderService;
import com.fiap.burguer.utils.CPFUtils;
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
public class OrderController {
    private final OrderService orderService;
    private final ClientRepository clientRepository; // Adicione esta linha

    public OrderController(OrderService orderService, ClientRepository clientRepository) {
        this.orderService = orderService;
        this.clientRepository = clientRepository;
    }

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
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        try {

            if (orderRequest.getItems() != null) {
                for (OrderRequest.OrderItemRequest item : orderRequest.getItems()) {
                    if (item.getIdCliente() != null) {

                            Client client = clientRepository.findById(item.getIdCliente())
                                    .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
                            orderRequest.setClient(client);

                    }

                    if (item.getQuantity() <= 0) {
                        String errorMessage = "A quantidade do item com ID " + item.getProductId() + " deve ser maior que zero.";
                        return ResponseEntity.badRequest().body(errorMessage);
                    }
                }
            }

            Order order = orderService.createOrder(orderRequest);
            OrderResponse response = orderService.mapOrderToResponse(order);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    @Operation(summary = "Consulta todos os pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrou pedidos",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Nenhum pedido encontrado",
                    content = @Content)})
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        if (orders == null || orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<OrderResponse> responses = orders.stream()
                .map(orderService::mapOrderToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

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
            @PathVariable("status") StatusOrder status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        if (orders == null || orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<OrderResponse> responses = orders.stream()
                .map(orderService::mapOrderToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

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
            @Parameter(description = "ID do pedido a ser consultado", required = true) @PathVariable("id") int id) {
        try {
            Order order = orderService.getOrderById(id);
            if (order == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            OrderResponse response = orderService.mapOrderToResponse(order);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
