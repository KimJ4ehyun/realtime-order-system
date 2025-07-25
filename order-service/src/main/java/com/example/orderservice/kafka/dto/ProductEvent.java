package com.example.orderservice.kafka.dto;

import com.example.orderservice.dto.OrderItemResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ProductEvent {
    private ProductEventType eventType;
    private Long paymentId;
    private Long orderId;
    private Long userId;
    private List<OrderItemResponse> items;
    private LocalDateTime createdAt;
}
