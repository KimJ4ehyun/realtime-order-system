package com.example.productservice.kafka.consumer;

import com.example.productservice.kafka.dto.PaymentEvent;
import com.example.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventListener {

    private final ProductService productService;

    @KafkaListener(topics = "payment-events", groupId = "product-service")
    public void handlePaymentEvent(@Payload PaymentEvent event) {
        try {
            log.info("결제 이벤트 수신: {}", event);

            productService.handlePaymentEvent(event);

            log.info("결제 이벤트 처리 완료 - OrderId: {}", event.getOrderId());

        } catch (Exception e) {
            log.error("결제 이벤트 처리 중 오류 발생 - PaymentEvent: {}", event, e);
            throw e;
        }
    }

}
