package com.example.shoestock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class OrderItem {
    private String shoeTypeId;
    private String size;
    private int quantity;
    private double unitPrice;
}
