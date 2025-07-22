package com.example.orderservice.entity;

import com.example.orderservice.client.dto.ProductResponse;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Long productId;

    private String productName;

    private Integer quantity;

    private Double price;

    public static OrderItem of(ProductResponse productResponse, Order order) {
        return OrderItem.builder()
                .order(order)
                .productId(productResponse.getProductId())
                .productName(productResponse.getProductName())
                .quantity(productResponse.getQuantity())
                .price(productResponse.getPrice())
                .build();
    }

}
