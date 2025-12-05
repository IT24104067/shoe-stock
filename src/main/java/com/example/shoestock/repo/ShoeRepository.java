package com.example.shoestock.repo;

import com.example.shoestock.model.ShoeType;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShoeRepository extends MongoRepository<ShoeType, String> {}
