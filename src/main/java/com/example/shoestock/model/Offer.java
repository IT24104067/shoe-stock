package com.example.shoestock.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Document(collection = "offers")
public class Offer {
    @Id
    private String id;
    private String shoeTypeId;
    private String size;
    private double discountedPrice;
    private Instant from;
    private Instant to;
    private String proposedByUserId;
    private OfferStatus status = OfferStatus.PENDING;
}
