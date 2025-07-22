package com.example.orderservice.client.dto;

import com.example.orderservice.dto.OrderItemRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class ProductOrderRequest {
    private Long productId;
    private Integer quantity;

    public static ProductOrderRequest from(OrderItemRequest request) {
        return new ProductOrderRequest(request.getProductId(), request.getQuantity());
    }
}
