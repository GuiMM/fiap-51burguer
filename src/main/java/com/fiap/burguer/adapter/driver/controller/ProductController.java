package com.fiap.burguer.adapter.driver.controller;
import com.fiap.burguer.core.application.dto.ProductCreate;
import com.fiap.burguer.adapter.driven.entities.ProductEntity;
import com.fiap.burguer.core.application.enums.CategoryProduct;
import com.fiap.burguer.core.application.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/{id}")
    @Operation(summary = "Consulta produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrou produto",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Id de produto invalido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content)})
    public @ResponseBody ResponseEntity<ProductEntity> getProductById(
            @Parameter(description = "ID do produto a ser consultado", required = true) @PathVariable("id") int id) {
        ProductEntity productEntity = productService.findById(id);
        if (productEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productEntity, HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Consulta produtos por categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrou produtos",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class)) }),
            @ApiResponse(responseCode = "400", description = "Categoria de produto inválida",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Produtos não encontrados para a categoria",
                    content = @Content) })
    public ResponseEntity<List<ProductEntity>> getProductsByCategory(
            @Parameter(description = "Categoria dos produtos a serem consultados", required = true) @PathVariable("category") CategoryProduct category) {
        try {
            List<ProductEntity> productEntities = productService.findByCategory(category);
            if (productEntities == null || productEntities.isEmpty()) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Custom-Message", "Nenhum produto encontrado para a categoria " + category);
                return ResponseEntity.notFound().headers(headers).build();
            }
            return ResponseEntity.ok(productEntities);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(name = "/create", produces = "application/json")
    @Operation(summary = "Cadastra produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto cadastrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Infos de produto invalido",
                    content = @Content)})
    public @ResponseBody ProductEntity postProduct(@RequestBody @Valid ProductCreate productCreate) {
        return productService.saveProduct(productCreate);
    }

    @PutMapping(name = "/update", produces = "application/json")
    @Operation(summary = "Atualiza produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Infos de produto invalido",
                    content = @Content)})
    public @ResponseBody ProductEntity putProduct(@Valid ProductEntity productEntity) {

        return productService.updateProduct(productEntity);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deleta produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletou produto",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Id de produto invalido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content)})
    public @ResponseBody ResponseEntity deleteProduct(
            @Parameter(description = "ID do produto a ser deletado", required = true) @PathVariable("id") int id) {

        ProductEntity productEntity = productService.findById(id);
        if (productEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            productService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

    }
}
