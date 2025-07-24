package com.example.orderservice.dto;

import com.example.orderservice.entity.OrderItem;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderItemResponse {
    private Long orderItemId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double amount;

    public static OrderItemResponse from(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .orderItemId(orderItem.getId())
                .productId(orderItem.getProductId())
                .productName(orderItem.getProductName())
                .quantity(orderItem.getQuantity())
                .amount(orderItem.getAmount())
                .build();
    }

    public static List<OrderItemResponse> from(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItemResponse::from)
                .toList();
    }
}
