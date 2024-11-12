package com.first.demo.controller;

import com.first.demo.common.PaymentRequest;
import com.first.demo.common.PaymentResponse;
import com.first.demo.entity.Payment;
import com.first.demo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/{paymentId}")
    public Optional<Payment> getPaymentById(@PathVariable int paymentId){
        return paymentService.getPaymentById(paymentId);

    }

    @PostMapping("/create")
    public ResponseEntity<List<Payment>> createPayment(@RequestBody List<Payment> payment){
        return paymentService.createPayment(payment);
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<Payment> updatePayment(@PathVariable int paymentId,@RequestBody Payment payment){
        return paymentService.updatePayment(paymentId,payment);
    }

    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest){

        PaymentResponse response=paymentService.processPayment(paymentRequest);
        return ResponseEntity.ok(response);

    }
}
