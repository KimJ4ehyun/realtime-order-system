package com.example.paymentservice.client;

import com.example.paymentservice.client.dto.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service")
public interface OrderServiceClient {

    @GetMapping("/api/v1/orders/{orderId}")
    OrderResponse getOrder(@PathVariable("orderId") Long orderId);
}

