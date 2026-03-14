package com.ljmu.msc.repository.orm;

import com.ljmu.msc.domain.Product;
import com.ljmu.msc.domain.entities.ProductEntity;
import com.ljmu.msc.repository.ProductRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("orm")
@Transactional
public class ProductRepositoryOrm implements ProductRepository {

    private final ProductJpaRepositoryExtension productJpaRepositoryExtension;

    public ProductRepositoryOrm(ProductJpaRepositoryExtension productJpaRepositoryExtension) {
        this.productJpaRepositoryExtension = productJpaRepositoryExtension;
    }


    @Override
    public Product save(Product product) {
        return productJpaRepositoryExtension
                .save(ProductEntity.fromDomain(product))
                .toDomain();
    }

    @Override
    public Optional<Product> findById(Long id) {
        ProductEntity productEntity =
                productJpaRepositoryExtension.findById(id).orElseThrow();
        return Optional.of(productEntity.toDomain());
    }

    @Override
    public List<Product> findAll() {
        List<ProductEntity> productEntities = productJpaRepositoryExtension.findAll();
        return productEntities.stream()
                .map(ProductEntity::toDomain)
                .toList();
    }

    @Override
    public void updateStock(Long productId, int newQuantity) {
        ProductEntity productEntity = productJpaRepositoryExtension
                .findById(productId).orElseThrow();
        productEntity.setStockQuantity(newQuantity);
        productJpaRepositoryExtension.save(productEntity);
    }

    @Override
    public void deleteById(Long id) {
        productJpaRepositoryExtension.deleteById(id);
    }
}
