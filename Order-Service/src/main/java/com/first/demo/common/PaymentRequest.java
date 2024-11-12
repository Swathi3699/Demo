package com.first.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private int orderId;
    private String paymentStatus;
    private double amount;
    private int userid;
}
