package com.fiap.burguer.adapter.driver.controller;
import com.fiap.burguer.core.application.dto.CheckoutResponse;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.service.CheckoutService;
import com.fiap.burguer.core.domain.CheckOut;
import com.fiap.burguer.core.domain.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    CheckoutService checkoutService;
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }
    @GetMapping("/{id}")
    @Operation(summary = "Consulta checkout do pedido por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta de checkout do Pedido, realizada com sucesso!",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CheckOut.class))}),
            @ApiResponse(responseCode = "400", description = "Não foi possível encontrar o checkout para este pedido!",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado, tente outro!",
                    content = @Content)})
    public @ResponseBody  ResponseEntity<?> getCheckoutOrderById(
            @Parameter(description = "ID do pedido a ser consultado", required = true) @PathVariable("id") int id) {
        try {
            CheckOut checkout = checkoutService.findById(id);
            Order order = checkoutService.findOrderById(checkout.getOrder().getId());
            CheckOut checkoutNew = checkoutService.mapOrderToCheckout(order, order.getStatus());
            CheckoutResponse response = checkoutService.mapCheckoutToResponse(checkoutNew);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/criar/{id}/{status_order}")
    @Operation(summary = "Cria checkout por ID do pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Checkout criado com sucesso!",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CheckOut.class))}),
            @ApiResponse(responseCode = "400", description = "Não foi possível criar o checkout para este pedido!",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Checkout não encontrado, tente outro!",
                    content = @Content)})
    public @ResponseBody ResponseEntity<?> postCheckout(
            @Parameter(description = "ID do pedido para criar checkout", required = true) @PathVariable("id") int id,
            @Parameter(description = "Status do Pedido", required = true, schema = @Schema(allowableValues = {"APPROVEDPAYMENT", "REJECTEDPAYMENT"}))
            @PathVariable("status_order") StatusOrder statusOrder) {

        CheckOut checkoutNew = checkoutService.createCheckout(id, statusOrder);
        CheckoutResponse response = checkoutService.mapCheckoutToResponse(checkoutNew);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
}
