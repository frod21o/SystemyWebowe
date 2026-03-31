package com.example.order_history_service.service;

import com.example.order_history_service.model.OrderHistory;
import com.example.order_history_service.repository.OrderHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderHistoryService {

    private final OrderHistoryRepository repository;

    public OrderHistory createOrderHistory(OrderHistory orderHistory) {
        return repository.save(orderHistory);
    }

    public OrderHistory updateDeliveryStatus(Long id, String newStatus) {
        OrderHistory order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono zamówienia o ID: " + id));

        order.setDeliveryStatus(newStatus);
        return repository.save(order);
    }

    public OrderHistory getOrderHistoryById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono zamówienia o ID: " + id));
    }

    public List<OrderHistory> getAllOrderHistory() {
        return repository.findAll();
    }

    public Page<OrderHistory> getOrderHistoryPaged(Pageable pageable) {
        return repository.findAll(pageable);
    }
}