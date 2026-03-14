package com.ljmu.msc.repository;

import com.ljmu.msc.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    void updateStock(Long productId, int newQuantity);

    void deleteById(Long id);
}
