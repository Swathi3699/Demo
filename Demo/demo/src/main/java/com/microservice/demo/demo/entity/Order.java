package com.microservice.demo.demo.entity;

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
@Table(name="ORDER_TBL")
public class Order {
    @Id
    private int id;
    private String name;
    private int qty;
    private double price;

}
