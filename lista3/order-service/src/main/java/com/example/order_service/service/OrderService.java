package com.example.order_service.service;

import com.example.order_service.model.DeliveryStatus;
import com.example.order_service.model.Item;
import com.example.order_service.model.Order;
import com.example.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;

    public Order createOrder(Order order) {
        return repository.save(order);
    }

    public Order updateStatus(Long id, DeliveryStatus status) {
        Order order = repository.findById(id).orElseThrow();
        order.getDelivery().setStatus(status);
        return repository.save(order);
    }
}
