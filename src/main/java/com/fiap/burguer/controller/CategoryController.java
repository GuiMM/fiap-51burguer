package com.fiap.burguer.controller;

import com.fiap.burguer.entities.Category;
import com.fiap.burguer.entities.Product;
import com.fiap.burguer.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(name = "/create", produces = "application/json")
    @Operation(summary = "Cadastra categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria cadastrada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Category.class)) }),
            @ApiResponse(responseCode = "400", description = "Infos de categoria inválida",
                    content = @Content)})
    public @ResponseBody Category postCategory(@Valid Category category) {
        return categoryService.saveCategoryOrUpdate(category);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontrou produto",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class)) }),
            @ApiResponse(responseCode = "400", description = "Id de produto invalido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content) })
    public @ResponseBody ResponseEntity<Category> getCategoryById(
            @Parameter(description = "ID da categoria a ser consultada", required = true) @PathVariable("id") int id) {
        Category category = categoryService.findById(id);
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

}
