package ru.platform.management.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.platform.management.core.model.entity.Order;
import ru.platform.management.core.repository.jpa.OrderRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupplierService {

    private final OrderRepository orderRepository;
    private final MedicationService medicationService;
    private final OrderService orderService;

    @Scheduled(fixedDelay = 60_000)
    public void processPendingOrders() {
        log.info("Supplier service: checking for pending orders...");

        List<Order> pendingOrders = orderRepository.findByStatus("PENDING");
        if (pendingOrders.isEmpty()) {
            log.info("No pending orders found.");
            return;
        }

        log.info("Found {} pending order(s) to process.", pendingOrders.size());

        for (Order order : pendingOrders) {
            try {
                medicationService.updateStock(order.getMedication().getId(), order.getQuantity());
                log.info("Stock increased for medication '{}' by {} units.",
                        order.getMedication().getName(), order.getQuantity());

                orderService.completeOrder(order);
                log.info("Order {} marked as COMPLETE.", order.getId());

            } catch (Exception e) {
                log.error("Failed to process order {}: {}", order.getId(), e.getMessage(), e);
            }
        }

        log.info("Supplier service finished processing pending orders.");
    }
}