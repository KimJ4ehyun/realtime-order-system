package com.example.paymentservice.client.dto;

import lombok.Getter;

@Getter
public class OrderItemResponse {
    private Long orderItemId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;
}
