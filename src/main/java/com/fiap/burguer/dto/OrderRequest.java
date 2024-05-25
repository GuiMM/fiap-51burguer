package com.fiap.burguer.dto;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fiap.burguer.entities.Client;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private List<OrderItemRequest> items;
    @JsonIgnore
    private Client client;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItemRequest {

        private Integer idCliente;

        @NotNull
        private int productId;

        @NotNull
        @Min(value = 1, message = "A quantidade deve ser maior que zero")
        private int quantity;


    }
}
