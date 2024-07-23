package com.fiap.burguer.driver.controller;
import com.fiap.burguer.api.ProductApi;
import com.fiap.burguer.driver.dto.ProductCreate;
import com.fiap.burguer.infraestructure.entities.ProductEntity;
import com.fiap.burguer.core.application.enums.CategoryProduct;
import com.fiap.burguer.core.application.usecases.ProductUseCases;
import com.fiap.burguer.core.domain.Product;
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
public class ProductController implements ProductApi {
    ProductUseCases productUseCases;
    public ProductController(ProductUseCases productUseCases) {
        this.productUseCases = productUseCases;
    }


    public ResponseEntity<Product> getProductById(int id) {
        Product product = productUseCases.findById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    public ResponseEntity<List<Product>> getProductsByCategory(CategoryProduct category) {
            List<Product> productEntities = productUseCases.findByCategory(category);
            return ResponseEntity.ok(productEntities);
    }

    public Product postProduct(ProductCreate productCreate) {
        return productUseCases.saveProduct(productCreate);
    }


    public Product putProduct(Product product) {
        return productUseCases.updateProduct(product);
    }


    public  ResponseEntity deleteProduct(int id) {

            productUseCases.deleteById(id);
            return new ResponseEntity<>("Produto Deletado com sucesso",HttpStatus.OK);

    }
}

