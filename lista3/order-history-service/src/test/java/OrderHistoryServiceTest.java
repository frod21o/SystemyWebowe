

import com.example.order_history_service.model.OrderHistory;
import com.example.order_history_service.repository.OrderHistoryRepository;
import com.example.order_history_service.service.OrderHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderHistoryServiceTest {

    @Mock
    private OrderHistoryRepository repository;

    @InjectMocks
    private OrderHistoryService service;

    private OrderHistory sampleOrder;

    @BeforeEach
    void setUp() {
        sampleOrder = OrderHistory.builder()
                .orderId(1L)
                .customerName("Jan Kowalski")
                .courierName("Bogdan Zły")
                .deliveryStatus("CREATED")
                .productNames("Myszka, Klawiatura")
                .totalPrice(new BigDecimal("250.00"))
                .build();
    }
    @Test
    void shouldCreateOrderHistory() {
        // given
        when(repository.save(any(OrderHistory.class))).thenReturn(sampleOrder);

        // when
        OrderHistory created = service.createOrderHistory(sampleOrder);

        // then
        assertNotNull(created);
        assertEquals("CREATED", created.getDeliveryStatus());
        verify(repository, times(1)).save(sampleOrder);
    }

    @Test
    void shouldUpdateDeliveryStatus() {
        // given
        when(repository.findById(1L)).thenReturn(Optional.of(sampleOrder));
        when(repository.save(any(OrderHistory.class))).thenReturn(sampleOrder);

        // when
        OrderHistory updated = service.updateDeliveryStatus(1L, "DELIVERED");

        // then
        assertEquals("DELIVERED", updated.getDeliveryStatus());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(sampleOrder);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingOrder() {
        // given
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // when & then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.updateDeliveryStatus(99L, "DELIVERED");
        });

        assertTrue(exception.getMessage().contains("Nie znaleziono"));
        verify(repository, never()).save(any());
    }

    @Test
    void shouldGetOrderHistoryById() {
        // given
        when(repository.findById(1L)).thenReturn(Optional.of(sampleOrder));

        // when
        OrderHistory found = service.getOrderHistoryById(1L);

        // then
        assertNotNull(found);
        assertEquals(1L, found.getOrderId());
    }
}