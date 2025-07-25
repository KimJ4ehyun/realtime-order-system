package com.example.productservice.kafka.dto;

import lombok.Getter;

@Getter
public class OrderItemResponse {
    private Long orderItemId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double amount;
}
