package com.microservice.demo.demo.common;

import com.microservice.demo.demo.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class TransactionResponse {
    private Order order;
    private double amount;
    private String transactionUID;
    private String message;
}
