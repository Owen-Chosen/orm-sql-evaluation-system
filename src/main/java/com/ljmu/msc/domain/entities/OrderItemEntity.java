package com.ljmu.msc.domain.entities;

import com.ljmu.msc.domain.OrderItem;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "unit_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;


    // --- getters & setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    // --- Domain Mapping ---

    public OrderItem toDomain() {
        OrderItem item = new OrderItem();
        item.setId(this.id);
        item.setOrderId(this.order.getId());
        item.setProductId(this.productId);
        item.setQuantity(this.quantity);
        item.setUnitPrice(this.unitPrice);
        return item;
    }

    public static OrderItemEntity fromDomain(OrderItem item, OrderEntity orderEntity) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setId(item.getId());
        orderItemEntity.setOrder(orderEntity);
        orderItemEntity.setProductId(item.getProductId());
        orderItemEntity.setQuantity(item.getQuantity());
        orderItemEntity.setUnitPrice(item.getUnitPrice());
        return orderItemEntity;
    }

}