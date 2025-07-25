package com.example.productservice.kafka.producer;

import com.example.productservice.kafka.dto.ProductEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductEventProducer {

    private final KafkaTemplate<String, ProductEvent> kafkaTemplate;

    public void sendProductEvent(ProductEvent event) {
        kafkaTemplate.send("product-events", event)
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
