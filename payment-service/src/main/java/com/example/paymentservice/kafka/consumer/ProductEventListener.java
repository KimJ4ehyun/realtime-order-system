package com.example.paymentservice.kafka.consumer;

import com.example.paymentservice.kafka.dto.ProductEvent;
import com.example.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductEventListener {

    private final PaymentService paymentService;

    @KafkaListener(topics = "product-events", groupId = "payment-service")
    public void handleProductEvent(@Payload ProductEvent event) {
        try {
            log.info("상품 이벤트 수신: {}", event);

            paymentService.handleProductEvent(event);

            log.info("상품 이벤트 처리 완료 - OrderId: {}", event.getOrderId());
        } catch (Exception e) {
            log.error("상품 이벤트 처리 중 오류 발생 - ProductEvent: {}", event, e);
            throw e;
        }
    }
}

