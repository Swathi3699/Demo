package com.microservice.demo.demo.controller;

import com.microservice.demo.demo.entity.Payment;
import com.microservice.demo.demo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;


@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/doPayment")
    public Payment doPayment(@RequestBody Payment payment) {

        return paymentService.doPayment(payment);
    }

    @GetMapping("/{orderId}")
    public Payment findPaymentById(@PathVariable int orderId){
        return  paymentService.findPaymentById(orderId);
    }


}
