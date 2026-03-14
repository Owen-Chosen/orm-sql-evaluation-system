package com.ljmu.msc.repository;
import com.ljmu.msc.domain.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(Long id);

    List<Order> findByUserId(Long userId);

    List<Order> findOrdersBetween(LocalDateTime start, LocalDateTime end);

    void deleteById(Long id);
}
