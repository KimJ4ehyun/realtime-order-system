package com.example.paymentservice.client.dto;

import lombok.Getter;

@Getter
public class ProductResponse {
    private Long productId;
    private String productName;
    private Double amount;
    private Integer quantity;
    private Boolean isStockValidated;
}
