package com.example.productservice.service;

import com.example.productservice.dto.ProductOrderRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.entity.Product;
import com.example.productservice.global.exception.CustomException;
import com.example.productservice.kafka.dto.*;
import com.example.productservice.kafka.producer.ProductEventProducer;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductEventProducer productEventProducer;

    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsWithStockValidation(List<ProductOrderRequest> requests) {
        List<Long> productIds = requests.stream()
                .map(ProductOrderRequest::getProductId)
                .toList();

        List<Product> products = productRepository.findAllById(productIds);

        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        return requests.stream()
                .map(request -> {
                    Product product = productMap.get(request.getProductId());

                    boolean isValid = product != null && product.isStockAvailable(request.getQuantity());

                    return ProductResponse.of(
                            request.getProductId(),
                            request.getQuantity(),
                            isValid,
                            product != null ? product.getAmount() : null
                    );
                })
                .toList();
    }

    public void handlePaymentEvent(PaymentEvent paymentEvent) {
        if (!PaymentEventType.PAYMENT_SUCCESS.equals(paymentEvent.getEventType())) {
            return;
        }

        List<Long> productIds = paymentEvent.getItems().stream()
                .map(OrderItemResponse::getProductId)
                .toList();

        List<Product> products = productRepository.findAllById(productIds);

        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        for (var item : paymentEvent.getItems()) {
            Product product = productMap.get(item.getProductId());

            if (product != null) {
                try {
                    product.decreaseStock(item.getQuantity());
                } catch (CustomException e) {
                    productEventProducer.sendProductEvent(
                            ProductEvent.of(ProductEventType.STOCK_DECREASE_FAILED, paymentEvent));
                    throw e;
                }
            }
        }

        productRepository.saveAll(products);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                productEventProducer.sendProductEvent(
                        ProductEvent.of(ProductEventType.STOCK_DECREASE_SUCCESS, paymentEvent));
            }
        });
    }

}
