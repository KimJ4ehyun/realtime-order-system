package com.example.productservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Boolean isStockValidated;
    private Double amount;

    public static ProductResponse of(Long productId, String productName, Integer quantity, boolean isValid, Double amount) {
        return ProductResponse.builder()
                .productId(productId)
                .productName(productName)
                .quantity(quantity)
                .isStockValidated(isValid)
                .amount(amount)
                .build();
    }
}