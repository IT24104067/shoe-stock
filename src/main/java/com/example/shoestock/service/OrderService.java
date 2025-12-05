package com.example.shoestock.service;

import com.example.shoestock.model.*;
import com.example.shoestock.repo.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final ShoeService shoeService;

    public OrderService(OrderRepository orderRepo, ShoeService shoeService) {
        this.orderRepo = orderRepo;
        this.shoeService = shoeService;
    }

    @Transactional
    public Order placeOrder(Order order) {
        order.setCreatedAt(Instant.now());
        order.setStatus(OrderStatus.PLACED);
        shoeService.decrementStockForOrder(order);
        return orderRepo.save(order);
    }

    @Transactional
    public Order returnOrder(String orderId) {
        Order o = orderRepo.findById(orderId).orElseThrow();
        if (o.getStatus() != OrderStatus.PLACED) throw new IllegalStateException("Order cannot be returned");
        shoeService.increaseStockOnReturn(o);
        o.setStatus(OrderStatus.RETURNED);
        return orderRepo.save(o);
    }
}
