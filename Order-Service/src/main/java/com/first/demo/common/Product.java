package com.first.demo.common;


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
@Table(name="PRODUCT")
public class Product {

    @Id
    private int productId;
    private String name;
    private String description;
    private double price;
    private Long stockQuantity;
}
