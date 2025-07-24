package com.example.productservice.dto;

import lombok.Getter;

@Getter
public class ProductOrderRequest {
    private Long productId;
    private Integer quantity;
}
