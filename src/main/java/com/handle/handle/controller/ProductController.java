package com.handle.handle.controller;


import com.handle.handle.domain.dto.ProductDto;
import com.handle.handle.domain.dto.ProductResponseDto;
import com.handle.handle.domain.enums.ProductStatus;
import com.handle.handle.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(@RequestParam(required = false) ProductStatus status) {
        return ResponseEntity.ok(this.productService.getAllProducts(status));
    }

    @PostMapping(" /create")
    public ResponseEntity<ProductResponseDto> saveProducts(@Valid @RequestBody ProductDto productDto) {
        return Optional.ofNullable(this.productService.saveProduct(productDto))
                .map(product -> ResponseEntity.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(product))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}