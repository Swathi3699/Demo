package com.first.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="PAYMENT_TABLE")
public class Payment {

    @Id
    private  int paymentId;
    private int orderId;
    private String paymentStatus;
    private String paymentMethod;
}
