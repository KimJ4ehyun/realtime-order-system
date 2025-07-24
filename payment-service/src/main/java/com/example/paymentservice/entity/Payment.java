package com.example.paymentservice.entity;

import com.example.paymentservice.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    private Long userId;

    private Long orderId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private Double totalAmount;

    private LocalDateTime paymentAt;

    public static Payment of(Long userId, Long orderId, Double totalAmount){
        return Payment.builder()
                .userId(userId)
                .orderId(orderId)
                .totalAmount(totalAmount)
                .build();
    }

    public void updatePaymentStatus(PaymentStatus status) {
        this.status = status;
        this.paymentAt = LocalDateTime.now();
    }
}
