package com.microservice.demo.demo.service;

import com.microservice.demo.demo.entity.Payment;
import com.microservice.demo.demo.repo.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Payment doPayment(Payment payment) {
        payment.setPaymentStatus(paymentProcessing());
        payment.setTransactionUID(UUID.randomUUID().toString());
        return paymentRepository.save(payment);
    }

    public String paymentProcessing(){

        return new Random().nextBoolean()?"success":"false";
    }

    public Payment findPaymentById(int orderId) {
        return  paymentRepository.findByOrderId(orderId);
    }
}
