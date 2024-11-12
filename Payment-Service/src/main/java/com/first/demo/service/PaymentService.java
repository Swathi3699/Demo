package com.first.demo.service;

import com.first.demo.common.PaymentRequest;
import com.first.demo.common.PaymentResponse;
import com.first.demo.entity.Payment;
import com.first.demo.repo.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepo paymentRepo;

    public Optional<Payment> getPaymentById(int paymentId) {
        return paymentRepo.findById(paymentId);
    }

    public ResponseEntity<List<Payment>> createPayment(List<Payment> payment) {
        List<Payment> records=paymentRepo.saveAll(payment);
        return ResponseEntity.ok(records);

    }

    @PostMapping("/{paymentId}")
    public ResponseEntity<Payment> updatePayment(@PathVariable int paymentId, @RequestBody Payment payment) {
        Optional<Payment> existingRecordOptional=paymentRepo.findById(paymentId);

        if(existingRecordOptional.isPresent()){
            Payment existingRecords=existingRecordOptional.get();

            existingRecords.setPaymentId(payment.getPaymentId());
            existingRecords.setOrderId(payment.getOrderId());
            existingRecords.setPaymentStatus(payment.getPaymentStatus());
            existingRecords.setPaymentMethod(payment.getPaymentMethod());

            Payment updateRecords=paymentRepo.save(existingRecords);
            return  ResponseEntity.ok(updateRecords);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        Random random=new Random();
        boolean paymentStatus=random.nextBoolean();
        String transactionId= UUID.randomUUID().toString();

        if(paymentStatus){
            return new PaymentResponse("SUCCESS",transactionId,paymentRequest.getOrderId());
        }
        else {
            return new PaymentResponse("FAIL",null, paymentRequest.getOrderId());
        }
    }
}
