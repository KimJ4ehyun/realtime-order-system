package com.example.productservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {
    private Long productId;
    private Integer quantity;
    private Boolean isStockValidated;
    private Double amount;

    public static ProductResponse of(Long productId, Integer quantity, boolean isValid, Double amount) {
        return ProductResponse.builder()
                .productId(productId)
                .quantity(quantity)
                .isStockValidated(isValid)
                .amount(amount)
                .build();
    }
}