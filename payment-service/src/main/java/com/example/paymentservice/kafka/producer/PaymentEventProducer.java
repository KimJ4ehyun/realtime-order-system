package com.example.paymentservice.kafka.producer;

import com.example.paymentservice.kafka.dto.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentEventProducer {

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public void sendPaymentEvent(PaymentEvent event) {
        kafkaTemplate.send("payment-events", event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Kafka 전송 성공: Partition {}, Offset {}, Key {}, Value {}",
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset(),
                                result.getProducerRecord().key(),
                                result.getProducerRecord().value());
                    } else {
                        log.error("Kafka 전송 실패!", ex);
                    }
                });
    }
}
