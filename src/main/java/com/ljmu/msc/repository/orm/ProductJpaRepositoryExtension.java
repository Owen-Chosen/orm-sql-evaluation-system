package com.ljmu.msc.repository.orm;

import com.ljmu.msc.domain.Product;
import com.ljmu.msc.domain.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductJpaRepositoryExtension extends JpaRepository<ProductEntity, Long> {
    @Modifying
    @Query("UPDATE ProductEntity p SET p.stockQuantity = :qty WHERE p.id = :id")
    void updateStock(@Param("id") Long id, @Param("qty") int qty);
}
