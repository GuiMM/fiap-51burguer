package com.fiap.burguer.adapter.driver.controller;
import com.fiap.burguer.core.application.dto.CheckoutResponse;
import com.fiap.burguer.adapter.driven.entities.CheckOut;
import com.fiap.burguer.adapter.driven.entities.Order;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.adapter.driven.repository.ClientRepository;
import com.fiap.burguer.core.application.service.CheckoutService;
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
    public CheckoutController(CheckoutService checkoutService, ClientRepository clientRepository) {
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

            if (checkout == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Order order = checkoutService.findOrderById(checkout.getOrder().getId());
            CheckOut checkoutNew = checkoutService.mapOrderToCheckout(order, order.getStatus());
            CheckoutResponse response = checkoutService.mapCheckoutToResponse(checkoutNew);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
        Order order = checkoutService.findOrderById(id);
        if(order.getStatus() == StatusOrder.RECEIVED|| order.getStatus() == StatusOrder.CANCELED){
            String errorMessage = "Não foi possível realizar o pagamento, pois o pedido " + order.getId() + " já está com o status "+ order.getStatus() ;
            return ResponseEntity.badRequest().body(errorMessage);
        }else{
            checkoutService.updateStatusOrder(order, statusOrder);
        }
        CheckOut checkoutNew = checkoutService.save(checkoutService.mapOrderToCheckout(order, statusOrder));
        try {
            if (checkoutNew == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            CheckoutResponse response = checkoutService.mapCheckoutToResponse(checkoutNew);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
