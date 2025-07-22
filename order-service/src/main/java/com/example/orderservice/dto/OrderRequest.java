package com.example.orderservice.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequest {
    private Long userId;
    private List<OrderItemRequest> items;
}
