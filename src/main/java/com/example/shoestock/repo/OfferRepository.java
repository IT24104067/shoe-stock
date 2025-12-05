package com.example.shoestock.repo;

import com.example.shoestock.model.Offer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OfferRepository extends MongoRepository<Offer, String> {}
