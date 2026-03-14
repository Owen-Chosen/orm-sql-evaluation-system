package com.ljmu.msc.service;

import com.ljmu.msc.domain.Order;
import com.ljmu.msc.domain.OrderItem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order placeOrder(Long userId, List<OrderItem> items);

    Optional<Order> getOrder(Long id);

    List<Order> getOrdersByUser(Long userId);

    List<Order> getOrdersBetween(LocalDateTime start, LocalDateTime end);

    void deleteOrder(Long id);
}
