package com.fiap.burguer.controller;

import com.fiap.burguer.entities.Product;
import com.fiap.burguer.service.ProductService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
public class ProductController {
    ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/all")
    ResponseEntity hello() {
        return ResponseEntity.ok().body("productService.findById(id)");
    }
    @GetMapping(name = "/{id}", produces = "application/json")
    public @ResponseBody Product getProductById(@PathVariable int id) {
        return productService.findById(id);
    }


    @PostMapping(name = "/create", produces = "application/json")
    public @ResponseBody Product postProduct(@Valid Product product) {
        return productService.saveProductOrUpdate(product);
    }

//    @PostMapping(name = "/update", produces = "application/json")
//    public @ResponseBody Product putProduct(@Valid Product product) {
//        return productService.saveProductOrUpdate(product);
//    }

    @DeleteMapping(name = "/delete", produces = "application/json")
    public @ResponseBody void deleteProduct(@PathVariable int id) {
        productService.deleteById(id);
    }
}

