package com.fiap.burguer.controller;

import com.fiap.burguer.entities.Category;
import com.fiap.burguer.entities.Product;
import com.fiap.burguer.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

}
