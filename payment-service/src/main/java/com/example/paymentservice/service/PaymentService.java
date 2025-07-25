package com.example.paymentservice.service;

import com.example.paymentservice.client.OrderServiceClient;
import com.example.paymentservice.client.ProductServiceClient;
import com.example.paymentservice.client.dto.OrderResponse;
import com.example.paymentservice.client.dto.ProductOrderRequest;
import com.example.paymentservice.client.dto.ProductResponse;
import com.example.paymentservice.dto.PaymentResponse;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.kafka.dto.EventType;
import com.example.paymentservice.kafka.dto.PaymentEvent;
import com.example.paymentservice.kafka.producer.PaymentEventProducer;
import com.example.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderServiceClient orderServiceClient;

    private final ProductServiceClient productServiceClient;

    private final PaymentEventProducer paymentEventProducer;

    public PaymentResponse processPayment(Long orderId) {
        OrderResponse order = orderServiceClient.getOrder(orderId);

        Payment newPayment = Payment.of(orderId, order.getUserId(), order.getTotalAmount());

        List<ProductOrderRequest> productOrderRequests = order.getItems().stream()
                .map(ProductOrderRequest::from)
                .toList();

        boolean allStockValid = productServiceClient.getProductsWithStockValidation(productOrderRequests).stream()
                .allMatch(ProductResponse::getIsStockValidated);

        Payment saved = paymentRepository.save(newPayment);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                EventType eventType = allStockValid ? EventType.PAYMENT_SUCCESS : EventType.PAYMENT_FAILED;
                paymentEventProducer.sendPaymentEvent(PaymentEvent.of(eventType, order));
            }
        });

        return PaymentResponse.from(saved);
    }
}
