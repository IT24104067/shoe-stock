package com.example.shoestock.model;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class ShoeSizeEntry {
    private String size;
    private double price;
    private int count;
}
