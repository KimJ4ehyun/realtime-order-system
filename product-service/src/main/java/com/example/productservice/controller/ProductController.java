package com.example.productservice.controller;

import com.example.productservice.dto.ProductOrderRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/stock/validate")
    ResponseEntity<List<ProductResponse>> getProductsWithStockValidation(@RequestBody List<ProductOrderRequest> requests) {
        return ResponseEntity.ok(productService.getProductsWithStockValidation(requests));
    }
}
