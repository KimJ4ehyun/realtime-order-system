package com.example.productservice.entity;

import com.example.productservice.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String productName;

    private String productDescription;

    private Integer stock;

    private Double amount;

    public boolean isStockAvailable(int quantity) {
        return stock >= quantity;
    }

    public void updateStock(int quantity) {
        stock -= quantity;
    }
}
