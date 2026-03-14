package com.ljmu.msc.controller;

import com.ljmu.msc.domain.Order;
import com.ljmu.msc.domain.OrderItem;
import com.ljmu.msc.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place")
    public Order placeOrder(
            @RequestParam Long userId,
            @RequestBody List<OrderItem> items) {

        return orderService.placeOrder(userId, items);
    }

    @GetMapping("/{id}")
    public Optional<Order> get(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @GetMapping("/user/{userId}")
    public List<Order> userOrders(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }
}
