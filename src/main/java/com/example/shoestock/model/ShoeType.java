package com.example.shoestock.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Document(collection = "shoes")
public class ShoeType {
    @Id
    private String id;
    private String name;
    private String description;
    private List<String> imageIds = new ArrayList<>();
    private List<ShoeSizeEntry> sizes = new ArrayList<>();
    private boolean active = true;
}
