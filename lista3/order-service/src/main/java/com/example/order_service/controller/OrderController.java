package com.example.order_service.controller;

import com.example.order_service.model.Order;
import com.example.order_service.model.DeliveryStatus;
import com.example.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping
    public Order create(@RequestBody Order order) {
        return service.createOrder(order);
    }

    @PatchMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id, @RequestParam DeliveryStatus status) {
        return service.updateStatus(id, status);
    }
}
