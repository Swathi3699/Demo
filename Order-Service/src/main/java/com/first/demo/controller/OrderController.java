package com.first.demo.controller;

import com.first.demo.common.OrderRequest;
import com.first.demo.common.ProductResponse;
import com.first.demo.entity.Order;
import com.first.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderId}")
    public Optional<Order> getOrderById(@PathVariable int orderId){
        return orderService.getOrderById(orderId);
    }

//    @PostMapping("/createOrder")
//    public ResponseEntity<List<Order>> createOrder(@RequestBody List<Order> order){
//        return orderService.createOrder(order);
//    }

    @PostMapping("/createOrder")
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest request){
        try{
            String result=orderService.createOrder(request);
            return ResponseEntity.ok(result);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable int orderId,@RequestBody Order order){
        return orderService.updateOrder(orderId, order);
    }
}
