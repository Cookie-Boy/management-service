package ru.platform.management.core.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.platform.management.core.model.entity.Order;
import ru.platform.management.core.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Заказ с UUID " + id + " не найден"));
    }

    @Transactional
    public Order updateOrder(Order order) {
        if (order.getId() == null || !orderRepository.existsById(order.getId())) {
            throw new EntityNotFoundException("Заказ с UUID " + order.getId() + " не найден");
        }
        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrderById(UUID id) {
        if (!orderRepository.existsById(id)) {
            throw new EntityNotFoundException("Заказ с UUID " + id + " не найден");
        }
        orderRepository.deleteById(id);
    }
}
