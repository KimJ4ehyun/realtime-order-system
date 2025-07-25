package com.example.paymentservice.service;

import com.example.paymentservice.client.OrderServiceClient;
import com.example.paymentservice.client.ProductServiceClient;
import com.example.paymentservice.client.dto.OrderResponse;
import com.example.paymentservice.client.dto.ProductOrderRequest;
import com.example.paymentservice.client.dto.ProductResponse;
import com.example.paymentservice.dto.PaymentResponse;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.entity.PaymentStatus;
import com.example.paymentservice.global.exception.CustomException;
import com.example.paymentservice.kafka.dto.PaymentEventType;
import com.example.paymentservice.kafka.dto.PaymentEvent;
import com.example.paymentservice.kafka.dto.ProductEvent;
import com.example.paymentservice.kafka.dto.ProductEventType;
import com.example.paymentservice.kafka.producer.PaymentEventProducer;
import com.example.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

import static com.example.paymentservice.global.exception.ErrorCode.PAYMENT_IS_NOT_EXISTS;

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

        Payment newPayment = Payment.of(order.getUserId(), orderId, order.getTotalAmount());

        List<ProductOrderRequest> productOrderRequests = order.getItems().stream()
                .map(ProductOrderRequest::from)
                .toList();

        boolean allStockValid = productServiceClient.getProductsWithStockValidation(productOrderRequests).stream()
                .allMatch(ProductResponse::getIsStockValidated);

        Payment saved = paymentRepository.save(newPayment);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                PaymentEventType paymentEventType = allStockValid ? PaymentEventType.PAYMENT_SUCCESS : PaymentEventType.PAYMENT_FAILED;
                paymentEventProducer.sendPaymentEvent(PaymentEvent.of(paymentEventType, saved.getId(), order));
            }
        });

        return PaymentResponse.from(saved);
    }

    public void handleProductEvent(ProductEvent event) {
        if (ProductEventType.STOCK_DECREASE_FAILED.equals(event.getEventType())) {
            Payment payment = paymentRepository.findById(event.getPaymentId())
                    .orElseThrow(() -> new CustomException(PAYMENT_IS_NOT_EXISTS));

            payment.updatePaymentStatus(PaymentStatus.FAILED);
        }
    }
}
