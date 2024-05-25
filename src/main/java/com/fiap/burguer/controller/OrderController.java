package com.fiap.burguer.controller;
import com.fiap.burguer.dto.OrderRequest;
import com.fiap.burguer.dto.OrderResponse;
import com.fiap.burguer.entities.Order;
import com.fiap.burguer.enums.StatusOrder;
import com.fiap.burguer.service.OrderService;
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

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @Operation(summary = "Cria um novo pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content)})
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        try {
            Order order = orderService.createOrder(orderRequest);
            OrderResponse response = orderService.mapOrderToResponse(order);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    @Operation(summary = "Consulta todos os pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrou pedidos",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class)) }),
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
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class)) }),
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
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Id de pedido inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content) })
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

