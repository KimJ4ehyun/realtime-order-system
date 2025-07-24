package com.example.paymentservice.client.dto;

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

    public static ProductOrderRequest from(OrderItemResponse request) {
        return new ProductOrderRequest(request.getProductId(), request.getQuantity());
    }
}
