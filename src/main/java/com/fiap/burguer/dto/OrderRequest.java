package com.fiap.burguer.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fiap.burguer.entities.Client;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Integer idCliente;

    @JsonIgnore
    private Client client;
    private List<OrderItemRequest> items;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItemRequest {

        private int productId;

        @Min(value = 1, message = "A quantidade deve ser maior que zero")
        private int quantity;

    }
}
