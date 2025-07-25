package com.example.productservice.entity;

import com.example.productservice.common.BaseEntity;
import com.example.productservice.global.exception.CustomException;
import jakarta.persistence.*;
import lombok.*;

import static com.example.productservice.global.exception.ErrorCode.PRODUCT_STOCK_IS_NOT_VALIDATED;

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
        return this.stock >= quantity;
    }

    public void decreaseStock(int quantity) {
        if (this.stock < quantity) {
            throw new CustomException(PRODUCT_STOCK_IS_NOT_VALIDATED);
        }
        this.stock -= quantity;
    }
}
