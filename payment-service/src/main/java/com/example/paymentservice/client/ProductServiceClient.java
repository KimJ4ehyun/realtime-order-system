package com.example.paymentservice.client;

import com.example.paymentservice.client.dto.ProductOrderRequest;
import com.example.paymentservice.client.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductServiceClient {

    @PostMapping("/api/v1/products/stock/validate")
    List<ProductResponse> getProductsInfo(@RequestBody List<ProductOrderRequest> requests);
}
