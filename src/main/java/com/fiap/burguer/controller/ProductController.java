package com.fiap.burguer.controller;

import com.fiap.burguer.entities.Product;
import com.fiap.burguer.enums.CategoryProduct;
import com.fiap.burguer.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    ProductService productService;

    @GetMapping(name = "/{id}", produces = "application/json")
    public @ResponseBody Product getProductById(@PathVariable int id) {
        return productService.findById(id);
    }

//    @GetMapping(name = "/{category}", produces = "application/json")
//    public @ResponseBody List<Product> getProductsByCategory(@PathVariable CategoryProduct categoryProduct) {
//        return productService.findByCategory(categoryProduct);
//    }
//    @GetMapping(name = "/all", produces = "application/json")
//    public @ResponseBody List<Product> getProducts() {
//        return productService.findAll();
//    }

    @PostMapping(name = "/create", produces = "application/json")
    public @ResponseBody Product postProduct(@Valid Product product) {
        return productService.saveProductOrUpdate(product);
    }

    @PostMapping(name = "/update", produces = "application/json")
    public @ResponseBody Product putProduct(@Valid Product product) {
        return productService.saveProductOrUpdate(product);
    }

    @DeleteMapping(name = "/delete", produces = "application/json")
    public @ResponseBody void deleteProduct(@PathVariable int id) {
        productService.deleteById(id);
    }
}

