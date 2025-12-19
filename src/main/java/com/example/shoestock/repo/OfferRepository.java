package com.example.shoestock.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.shoestock.model.Offer;
import com.example.shoestock.model.OfferStatus;

public interface OfferRepository extends MongoRepository<Offer, String> {
	long countByStatus(OfferStatus status);
}
