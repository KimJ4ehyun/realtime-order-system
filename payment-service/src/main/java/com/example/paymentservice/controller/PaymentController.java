package com.example.paymentservice.controller;

import com.example.paymentservice.dto.PaymentResponse;
import com.example.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/orders/{orderId}")
    public ResponseEntity<PaymentResponse> processPayment(@PathVariable Long orderId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.processPayment(orderId));
    }
}
