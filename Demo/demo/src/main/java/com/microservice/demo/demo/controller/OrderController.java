package com.microservice.demo.demo.controller;


import com.microservice.demo.demo.common.TransactionRequest;
import com.microservice.demo.demo.common.TransactionResponse;
import com.microservice.demo.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @PostMapping("/bookOrder")
    public TransactionResponse createOrder(@RequestBody TransactionRequest request) {

        return orderService.saveOrder(request);
    }

}