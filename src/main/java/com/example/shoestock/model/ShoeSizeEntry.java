package com.example.shoestock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ShoeSizeEntry {
    private String size;
    private double price;
    private int count;
}
