package com.example.order_history_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderHistory {

    @Id
    private Long orderId;

    private String customerName;
    private String courierName;
    private String deliveryStatus;
    private String productNames;
    private BigDecimal totalPrice;
}
