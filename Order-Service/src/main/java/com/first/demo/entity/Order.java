package com.first.demo.entity;

import com.first.demo.common.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ORDER_SERVICE")
public class Order {

    @Id
    private  int orderId;
    private double totalAmount;
    private String status;
    private int userId;


}
