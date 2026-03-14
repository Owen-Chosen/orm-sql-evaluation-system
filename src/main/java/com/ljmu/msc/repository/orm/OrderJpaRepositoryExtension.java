package com.ljmu.msc.repository.orm;

import com.ljmu.msc.domain.Order;
import com.ljmu.msc.domain.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderJpaRepositoryExtension extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByUserId(Long userId);

    List<OrderEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
