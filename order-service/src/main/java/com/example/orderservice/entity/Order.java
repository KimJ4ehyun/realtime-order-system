package com.example.orderservice.entity;

import com.example.orderservice.common.BaseEntity;
import com.example.orderservice.dto.OrderRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // 주문 총 금액
    private Double totalAmount;

    // 주문 상품 목록
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    public static Order from(OrderRequest request) {
        return Order.builder()
                .userId(request.getUserId())
                .status(OrderStatus.PENDING)
                .build();
    }

    public void addOrderItem(OrderItem item) {
        this.items.add(item);
    }

    public void updateOrderDetails(Double totalAmount, OrderStatus orderStatus) {
        this.totalAmount = totalAmount;
        this.status = orderStatus;
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }
}
