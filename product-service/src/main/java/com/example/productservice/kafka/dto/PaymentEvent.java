package com.example.productservice.kafka.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PaymentEvent {
    private PaymentEventType eventType;
    private Long paymentId;
    private Long orderId;
    private Long userId;
    private List<OrderItemResponse> items;
    private LocalDateTime createdAt;
}
