package com.example.order_service.service;

import com.example.order_service.client.api.OrderHistoryApi;
import com.example.order_service.client.model.OrderHistory;
import com.example.order_service.model.DeliveryStatus;
import com.example.order_service.model.Order;
import com.example.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderHistoryApi orderHistoryApi; // Wstrzykujemy wygenerowanego klienta z Zadania 4

    public Order createOrder(Order order) {
        // 1. Zapisz w lokalnej bazie Order Service
        Order savedOrder = repository.save(order);

        // 2. Synchronizacja: Wyślij do OrderHistory Service
        try {
            OrderHistory historyPayload = mapToOrderHistory(savedOrder);
            orderHistoryApi.createHistory(historyPayload); // Wywołanie REST API pod spodem!
            log.info("Zsynchronizowano utworzenie zamówienia o ID: {}", savedOrder.getId());
        } catch (Exception e) {
            log.error("Błąd synchronizacji (tworzenie) z OrderHistory Service: {}", e.getMessage());
        }

        return savedOrder;
    }

    public Order updateStatus(Long id, DeliveryStatus status) {
        // 1. Zaktualizuj w lokalnej bazie Order Service
        Order order = repository.findById(id).orElseThrow();
        order.getDelivery().setStatus(status);
        Order updatedOrder = repository.save(order);

        // 2. Synchronizacja: Wyślij aktualizację statusu do OrderHistory Service
        try {
            orderHistoryApi.updateStatus(id, status.name());
            log.info("Zsynchronizowano status dla zamówienia ID {}: {}", id, status.name());
        } catch (Exception e) {
            log.error("Błąd synchronizacji (status) z OrderHistory Service: {}", e.getMessage());
        }

        return updatedOrder;
    }

    // --- Prywatna metoda pomocnicza do mapowania danych ---
    private OrderHistory mapToOrderHistory(Order order) {
        OrderHistory dto = new OrderHistory();

        dto.setOrderId(order.getId());
        dto.setCustomerName(order.getCustomerName());
        dto.setCourierName(order.getDelivery().getCourierName());
        dto.setDeliveryStatus(order.getDelivery().getStatus().name());

        // Łączenie nazw produktów w jeden string
        String productsCsv = order.getOrderItems().stream()
                .map(product -> product.getItem().getName()) // zakładam, że produkt ma pole name
                .collect(Collectors.joining(", "));
        dto.setProductNames(productsCsv);

        // Sumowanie wartości z produktów:
        BigDecimal total = order.getOrderItems().stream()
                .map(item -> {
                    BigDecimal price = item.getItem().getPrice();
                    BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
                    return price.multiply(quantity);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dto.setTotalPrice(total);

        return dto;
    }
}