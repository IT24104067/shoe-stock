package com.example.shoestock.controller;

import com.example.shoestock.model.Order;
import com.example.shoestock.service.OrderService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService s) { this.orderService = s; }

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.placeOrder(order));
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<?> returnOrder(@PathVariable String id) {
        return ResponseEntity.ok(orderService.returnOrder(id));
    }
}
