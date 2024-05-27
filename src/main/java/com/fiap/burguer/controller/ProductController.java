package com.fiap.burguer.controller;
import com.fiap.burguer.dto.ProductCreate;
import com.fiap.burguer.entities.Product;
import com.fiap.burguer.enums.CategoryProduct;
import com.fiap.burguer.service.ProductService;
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
                            schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Id de produto invalido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content)})
    public @ResponseBody ResponseEntity<Product> getProductById(
            @Parameter(description = "ID do produto a ser consultado", required = true) @PathVariable("id") int id) {
        Product product = productService.findById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
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
    public ResponseEntity<List<Product>> getProductsByCategory(
            @Parameter(description = "Categoria dos produtos a serem consultados", required = true) @PathVariable("category") CategoryProduct category) {
        try {
            List<Product> products = productService.findByCategory(category);
            if (products == null || products.isEmpty()) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Custom-Message", "Nenhum produto encontrado para a categoria " + category);
                return ResponseEntity.notFound().headers(headers).build();
            }
            return ResponseEntity.ok(products);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(name = "/create", produces = "application/json")
    @Operation(summary = "Cadastra produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto cadastrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Infos de produto invalido",
                    content = @Content)})
    public @ResponseBody Product postProduct(@RequestBody @Valid ProductCreate productCreate) {
        return productService.saveProduct(productCreate);
    }

    @PutMapping(name = "/update", produces = "application/json")
    @Operation(summary = "Atualiza produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Infos de produto invalido",
                    content = @Content)})
    public @ResponseBody Product putProduct(@Valid Product product) {

        return productService.updateProduct(product);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deleta produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletou produto",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Id de produto invalido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content)})
    public @ResponseBody ResponseEntity deleteProduct(
            @Parameter(description = "ID do produto a ser deletado", required = true) @PathVariable("id") int id) {

        Product product = productService.findById(id);
        if (product == null) {
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

