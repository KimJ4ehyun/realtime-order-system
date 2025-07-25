package com.example.productservice.kafka.dto;

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

    public static ProductEvent of(ProductEventType eventType, PaymentEvent paymentEvent) {
        return ProductEvent.builder()
                .eventType(eventType)
                .paymentId(paymentEvent.getPaymentId())
                .paymentId(paymentEvent.getPaymentId())
                .orderId(paymentEvent.getOrderId())
                .userId(paymentEvent.getUserId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
