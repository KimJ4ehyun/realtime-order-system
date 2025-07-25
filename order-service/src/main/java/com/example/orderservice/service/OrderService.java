package com.example.orderservice.service;

import com.example.orderservice.client.ProductServiceClient;
import com.example.orderservice.client.dto.ProductOrderRequest;
import com.example.orderservice.client.dto.ProductResponse;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderItem;
import com.example.orderservice.entity.OrderStatus;
import com.example.orderservice.global.exception.CustomException;
import com.example.orderservice.kafka.dto.ProductEvent;
import com.example.orderservice.kafka.dto.ProductEventType;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.orderservice.global.exception.ErrorCode.ORDER_IS_NOT_EXISTS;
import static com.example.orderservice.global.exception.ErrorCode.PRODUCT_STOCK_IS_NOT_VALIDATED;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductServiceClient productServiceClient;

    public OrderResponse createOrder(OrderRequest request) {
        Order newOrder = Order.from(request);

        List<ProductOrderRequest> productOrderRequests = request.getItems().stream()
                .map(ProductOrderRequest::from)
                .toList();

        List<ProductResponse> productResponses = productServiceClient.getProductsWithStockValidation(productOrderRequests);

        double totalAmount = 0;
        for (ProductResponse productResponse : productResponses) {
            if(productResponse.getIsStockValidated()) {
                totalAmount += productResponse.getAmount() * productResponse.getQuantity();

                newOrder.addOrderItem(OrderItem.of(productResponse, newOrder));
            } else {
                throw new CustomException(PRODUCT_STOCK_IS_NOT_VALIDATED);
            }
        }

        newOrder.updateOrderDetails(totalAmount, OrderStatus.CREATED);

        return OrderResponse.from(orderRepository.save(newOrder));
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderResponse::from)
                .toList();
    }

    public void handleProductEvent(ProductEvent event) {
        Order order = orderRepository.findById(event.getOrderId())
                .orElseThrow(() -> new CustomException(ORDER_IS_NOT_EXISTS));

        if(ProductEventType.STOCK_DECREASE_SUCCESS.equals(event.getEventType())) {
            order.updateOrderStatus(OrderStatus.PAID);
        } else {
            order.updateOrderStatus(OrderStatus.FAILED);
        }
    }
}
