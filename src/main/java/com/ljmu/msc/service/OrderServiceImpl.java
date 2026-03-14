package com.ljmu.msc.service;

import com.ljmu.msc.domain.Order;
import com.ljmu.msc.domain.OrderItem;
import com.ljmu.msc.domain.Product;
import com.ljmu.msc.repository.OrderItemRepository;
import com.ljmu.msc.repository.OrderRepository;
import com.ljmu.msc.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            ProductRepository productRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Order placeOrder(Long userId, List<OrderItem> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : items) {
            total = total.add(
                    item.getUnitPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()))
            );
        }
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(total);
        order.setStatus("PLACED");

        Order savedOrder = orderRepository.save(order);

        for (OrderItem item : items) {

            item.setOrderId(savedOrder.getId());

            orderItemRepository.save(item);

            // Update stock
            Product product = productRepository
                    .findById(item.getProductId())
                    .orElseThrow();

            int newStock =
                    product.getStockQuantity() - item.getQuantity();

            productRepository.updateStock(product.getId(), newStock);
        }

        return savedOrder;
    }

    @Override
    public Optional<Order> getOrder(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> getOrdersBetween(LocalDateTime start, LocalDateTime end) {

        return orderRepository.findOrdersBetween(start, end);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}