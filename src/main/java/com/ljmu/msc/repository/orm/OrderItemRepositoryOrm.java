package com.ljmu.msc.repository.orm;

import com.ljmu.msc.domain.OrderItem;
import com.ljmu.msc.domain.entities.OrderEntity;
import com.ljmu.msc.domain.entities.OrderItemEntity;
import com.ljmu.msc.repository.OrderItemRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("orm")
public class OrderItemRepositoryOrm implements OrderItemRepository {

    private final OrderItemJpaRepositoryExtension orderItemJpaRepositoryExtension;
    private final OrderJpaRepositoryExtension orderJpaRepositoryExtension;

    public OrderItemRepositoryOrm(OrderItemJpaRepositoryExtension orderItemJpaRepositoryExtension,
                                  OrderJpaRepositoryExtension orderJpaRepositoryExtension) {
        this.orderItemJpaRepositoryExtension = orderItemJpaRepositoryExtension;
        this.orderJpaRepositoryExtension = orderJpaRepositoryExtension;
    }

    @Override
    public OrderItem save(OrderItem item) {
        OrderEntity orderEntity = orderJpaRepositoryExtension.findById(item.getOrderId()).orElseThrow();
        OrderItemEntity orderItemEntity = OrderItemEntity.fromDomain(item, orderEntity);
        return orderItemJpaRepositoryExtension.save(orderItemEntity).toDomain();
    }

    @Override
    public List<OrderItem> findByOrderId(Long orderId) {
        return orderItemJpaRepositoryExtension.findByOrder_Id(orderId)
                .stream()
                .map(OrderItemEntity::toDomain)
                .toList();
    }

    @Override
    public void deleteByOrderId(Long orderId) {
        orderItemJpaRepositoryExtension.deleteByOrder_Id(orderId);
    }
}
