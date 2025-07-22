package com.example.orderservice.dto;

import lombok.Getter;

@Getter
public class OrderItemRequest {
    private Long productId;
    private Integer quantity;
}
