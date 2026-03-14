package com.ljmu.msc.repository.orm;


import com.ljmu.msc.domain.OrderItem;
import com.ljmu.msc.domain.entities.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemJpaRepositoryExtension extends JpaRepository<OrderItemEntity, Long> {

    List<OrderItemEntity> findByOrder_Id(Long orderId);

    void deleteByOrder_Id(Long orderId);
}
