package com.example.paymentservice.client.dto;

public enum OrderStatus {
    PENDING,     // 주문 요청 상태
    CREATED,     // 주문서 생성 완료
    PAID,        // 결제 완료
    SHIPPED,     // 배송 시작
    COMPLETED,   // 배송 완료 및 거래 종료
    CANCELLED,   // 주문 취소됨
    FAILED       // 결제 실패, 시스템 에러 등
}

