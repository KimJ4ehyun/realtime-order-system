package com.example.paymentservice.client.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private OrderStatus status;
    private Double totalAmount;
    private List<OrderItemResponse> items;
}
