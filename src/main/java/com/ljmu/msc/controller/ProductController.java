package com.ljmu.msc.controller;

import com.ljmu.msc.domain.Product;
import com.ljmu.msc.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/{id}/stock")
    public void updateStock(
            @PathVariable Long id,
            @RequestParam int quantity) {

        productService.updateStock(id, quantity);
    }
}
