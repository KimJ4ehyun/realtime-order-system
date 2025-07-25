package com.example.productservice.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    PRODUCT_STOCK_IS_NOT_VALIDATED(HttpStatus.BAD_REQUEST, "주문량보다 재고 개수가 적습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
