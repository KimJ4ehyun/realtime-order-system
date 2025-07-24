package com.example.orderservice.client;

import com.example.orderservice.client.dto.ProductOrderRequest;
import com.example.orderservice.client.dto.ProductResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductServiceClient {

    @PostMapping("/api/v1/products/stock/validate")
    List<ProductResponse> getProductsWithStockValidation(@RequestBody List<ProductOrderRequest> requests);
}
