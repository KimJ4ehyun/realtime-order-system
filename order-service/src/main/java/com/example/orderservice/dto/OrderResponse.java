package com.example.orderservice.dto;

import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private OrderStatus status;
    private Double totalAmount;
    private List<OrderItemResponse> items;

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .items(OrderItemResponse.from(order.getItems()))
                .build();
    }
}
