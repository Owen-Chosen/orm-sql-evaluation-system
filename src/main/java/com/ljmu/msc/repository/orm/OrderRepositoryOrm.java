package com.ljmu.msc.repository.orm;

import com.ljmu.msc.domain.Order;
import com.ljmu.msc.domain.entities.OrderEntity;
import com.ljmu.msc.domain.entities.UserEntity;
import com.ljmu.msc.repository.OrderRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("orm")
public class OrderRepositoryOrm implements OrderRepository {

    public final OrderJpaRepositoryExtension orderJpaRepositoryExtension;
    public final UserJpaRepositoryExtension userJpaRepositoryExtension;

    public OrderRepositoryOrm(OrderJpaRepositoryExtension orderJpaRepositoryExtension,
                              UserJpaRepositoryExtension userJpaRepositoryExtension) {
        this.orderJpaRepositoryExtension = orderJpaRepositoryExtension;
        this.userJpaRepositoryExtension = userJpaRepositoryExtension;
    }

    @Override
    public Order save(Order order) {
        UserEntity userEntity = userJpaRepositoryExtension
                .findById(order.getUserId()).orElseThrow();
        OrderEntity orderEntity = OrderEntity.fromDomain(order, userEntity);
        return orderJpaRepositoryExtension.save(orderEntity).toDomain();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.of(orderJpaRepositoryExtension
                .findById(id).orElseThrow().toDomain());
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        List<OrderEntity> orderEntityList = orderJpaRepositoryExtension
                .findByUserId(userId);
        return orderEntityList.stream()
                .map(OrderEntity::toDomain).toList();
    }

    @Override
    public List<Order> findOrdersBetween(LocalDateTime start, LocalDateTime end) {
        List<OrderEntity> orderEntityList = orderJpaRepositoryExtension
                .findByCreatedAtBetween(start, end);
        return orderEntityList.stream().map(OrderEntity::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        orderJpaRepositoryExtension.deleteById(id);
    }
}
