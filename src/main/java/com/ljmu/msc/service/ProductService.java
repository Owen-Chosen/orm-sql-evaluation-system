package com.ljmu.msc.service;

import com.ljmu.msc.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product createProduct(Product product);

    Optional<Product> getProduct(Long id);

    List<Product> getAllProducts();

    void updateStock(Long productId, int quantity);

    void deleteProduct(Long id);
}
