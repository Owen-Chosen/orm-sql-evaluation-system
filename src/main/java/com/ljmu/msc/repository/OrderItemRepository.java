package com.ljmu.msc.repository;

import com.ljmu.msc.domain.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

public interface OrderItemRepository {

    OrderItem save(OrderItem item);

    List<OrderItem> findByOrderId(Long orderId);

    void deleteByOrderId(Long orderId);
}
