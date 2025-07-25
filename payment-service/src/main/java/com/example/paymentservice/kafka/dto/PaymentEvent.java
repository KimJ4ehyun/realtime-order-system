package com.example.paymentservice.kafka.dto;

import com.example.paymentservice.client.dto.OrderItemResponse;
import com.example.paymentservice.client.dto.OrderResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PaymentEvent {
    private PaymentEventType eventType;
    private Long paymentId;
    private Long orderId;
    private Long userId;
    private List<OrderItemResponse> items;
    private LocalDateTime createdAt;

    public static PaymentEvent of(PaymentEventType paymentEventType, Long paymentId, OrderResponse order) {
        return PaymentEvent.builder()
                .eventType(paymentEventType)
                .paymentId(paymentId)
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .items(order.getItems())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
