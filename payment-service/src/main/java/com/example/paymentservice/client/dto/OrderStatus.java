package com.example.paymentservice.client.dto;

public enum OrderStatus {
    PENDING,        // 주문 접수됨 (처리 대기)
    CREATED,        // 주문서 생성 완료 (재고 확보)
    PAID,           // 결제 완료됨
    SHIPPED,        // 상품 발송됨 (택배사 인계)
    IN_TRANSIT,     // 배송 중 (물류 이동)
    DELIVERING,     // 배송 기사 배정 (오늘 배달 예정)
    DELIVERED,      // 배송 완료됨
    CANCELLED,      // 주문 취소됨
    FAILED          // 주문 처리 실패
}

