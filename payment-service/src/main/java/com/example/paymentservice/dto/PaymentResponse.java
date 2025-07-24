package com.example.paymentservice.dto;

import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.entity.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PaymentResponse {
    private Long paymentId;
    private Long userId;
    private Long orderId;
    private PaymentStatus status;
    private Double totalAmount;
    private LocalDateTime paymentAt;

    public static PaymentResponse from(Payment payment) {
        return PaymentResponse.builder()
                .paymentId(payment.getId())
                .userId(payment.getUserId())
                .orderId(payment.getOrderId())
                .status(payment.getStatus())
                .totalAmount(payment.getTotalAmount())
                .paymentAt(payment.getPaymentAt())
                .build();
    }
}
