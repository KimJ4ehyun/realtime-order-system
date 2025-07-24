package com.example.paymentservice.service;

import com.example.paymentservice.client.OrderServiceClient;
import com.example.paymentservice.client.ProductServiceClient;
import com.example.paymentservice.client.dto.OrderResponse;
import com.example.paymentservice.client.dto.ProductOrderRequest;
import com.example.paymentservice.client.dto.ProductResponse;
import com.example.paymentservice.dto.PaymentResponse;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.entity.PaymentStatus;
import com.example.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderServiceClient orderServiceClient;

    private final ProductServiceClient productServiceClient;

    public PaymentResponse processPayment(Long orderId) {
        OrderResponse order = orderServiceClient.getOrder(orderId);

        Payment newPayment = Payment.of(orderId, order.getUserId(), order.getTotalAmount());

        List<ProductOrderRequest> productOrderRequests = order.getItems().stream()
                .map(ProductOrderRequest::from)
                .toList();

        boolean allStockValid = productServiceClient.getProductsInfo(productOrderRequests).stream()
                .allMatch(ProductResponse::getIsStockValidated);

        if (allStockValid) {
            newPayment.updatePaymentStatus(PaymentStatus.SUCCESS);
        } else {
            newPayment.updatePaymentStatus(PaymentStatus.FAILED);
        }

        return PaymentResponse.from(paymentRepository.save(newPayment));
    }
}
