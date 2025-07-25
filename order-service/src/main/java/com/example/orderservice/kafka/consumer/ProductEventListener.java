package com.example.orderservice.kafka.consumer;

import com.example.orderservice.kafka.dto.ProductEvent;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductEventListener {

    private final OrderService orderService;

    @KafkaListener(topics = "product-events", groupId = "order-service")
    public void handleProductEvent(@Payload ProductEvent event) {
        try {
            log.info("상품 이벤트 수신: {}", event);

            orderService.handleProductEvent(event);

            log.info("상품 이벤트 처리 완료 - OrderId: {}", event.getOrderId());
        } catch (Exception e) {
            log.error("상품 이벤트 처리 중 오류 발생 - ProductEvent: {}", event, e);
            throw e;
        }
    }
}

