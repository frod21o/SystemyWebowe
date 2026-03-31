package com.example.order_history_service.controller;

import com.example.order_history_service.model.OrderHistory;
import com.example.order_history_service.service.OrderHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
@Tag(name = "Order History", description = "API do zarządzania historią zamówień")
public class OrderHistoryController {

    private final OrderHistoryService service;
    private final PagedResourcesAssembler<OrderHistory> pagedResourcesAssembler;

    @PostMapping
    @Operation(summary = "Utwórz wpis w historii zamówień", tags = {"Operacje Zapisujące"})
    public ResponseEntity<OrderHistory> createHistory(@RequestBody OrderHistory orderHistory) {
        OrderHistory created = service.createOrderHistory(orderHistory);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Zaktualizuj status dostawy", tags = {"Operacje Zapisujące"})
    public ResponseEntity<OrderHistory> updateStatus(@PathVariable Long id, @RequestParam String status) {
        OrderHistory updated = service.updateDeliveryStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pobierz zamówienie po ID", tags = {"Operacje Odczytu"})
    public ResponseEntity<OrderHistory> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOrderHistoryById(id));
    }

    @GetMapping("/all")
    @Operation(summary = "Pobierz wszystkie zamówienia (bez paginacji)", tags = {"Operacje Odczytu"})
    public ResponseEntity<List<OrderHistory>> getAll() {
        return ResponseEntity.ok(service.getAllOrderHistory());
    }

    @GetMapping
    @Operation(summary = "Pobierz zamówienia (z paginacją i HATEOAS)", tags = {"Operacje Odczytu"})
    public ResponseEntity<PagedModel<EntityModel<OrderHistory>>> getPaged(Pageable pageable) {
        Page<OrderHistory> page = service.getOrderHistoryPaged(pageable);
        PagedModel<EntityModel<OrderHistory>> pagedModel = pagedResourcesAssembler.toModel(page);
        return ResponseEntity.ok(pagedModel);
    }
}
