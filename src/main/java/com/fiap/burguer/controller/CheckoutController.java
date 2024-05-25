package com.fiap.burguer.controller;

import com.fiap.burguer.dto.OrderResponse;
import com.fiap.burguer.entities.CheckOut;
import com.fiap.burguer.entities.Order;
import com.fiap.burguer.entities.Product;
import com.fiap.burguer.service.CheckoutService;
import com.fiap.burguer.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    CheckoutService checkoutService;

    @GetMapping("/{idOrder}")
    @Operation(summary = "Consulta checkout do pedido por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta de checkout do Pedido, realizada com sucesso!",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Não foi possível encontrar o checkout para este pedido!",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado, tente outro!",
                    content = @Content)})
    public @ResponseBody ResponseEntity<CheckOut> getCheckoutOrderById(
            @Parameter(description = "ID do pedido a ser consultado", required = true) @PathVariable("id") int id) {
        CheckOut checkout = checkoutService.findById(id);
        if (checkout == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(checkout, HttpStatus.OK);
    }

}
