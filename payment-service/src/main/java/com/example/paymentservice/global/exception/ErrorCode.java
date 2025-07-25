package com.example.paymentservice.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    PRODUCT_STOCK_IS_NOT_VALIDATED(HttpStatus.BAD_REQUEST, "주문량보다 재고 개수가 적습니다."),
    PAYMENT_IS_NOT_EXISTS(HttpStatus.NOT_FOUND, "요청한 결제 정보가 존재하지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
