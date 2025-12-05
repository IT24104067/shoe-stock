package com.example.shoestock.model;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class OrderItem {
    private String shoeTypeId;
    private String size;
    private int quantity;
    private double unitPrice;
}
