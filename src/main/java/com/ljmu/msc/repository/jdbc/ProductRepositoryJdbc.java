package com.ljmu.msc.repository.jdbc;

import com.ljmu.msc.domain.Product;
import com.ljmu.msc.repository.ProductRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class ProductRepositoryJdbc implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product save(Product product) {

        String sql = """
                INSERT INTO products (name, price, stock_quantity, created_at)
                VALUES (?, ?, ?, ?)
                RETURNING id
                """;
        Long generatedId = jdbcTemplate.queryForObject(
                sql,
                Long.class,
                product.getName(),
                product.getPrice(),
                product.getStockQuantity(),
                Timestamp.valueOf(
                        product.getCreatedAt() != null
                                ? product.getCreatedAt()
                                : LocalDateTime.now()
                )
        );
        product.setId(generatedId);
        return product;
    }

    @Override
    public Optional<Product> findById(Long id) {

        String sql = "SELECT * FROM products WHERE id = ?";
        List<Product> results = jdbcTemplate.query(sql, productRowMapper(), id);
        return results.stream().findFirst();
    }

    @Override
    public List<Product> findAll() {

        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, productRowMapper());
    }

    @Override
    public void updateStock(Long productId, int newQuantity) {

        String sql = """
                UPDATE products
                SET stock_quantity = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, newQuantity, productId);
    }

    @Override
    public void deleteById(Long id) {

        String sql = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Product> productRowMapper() {

        return (rs, rowNum) -> {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getBigDecimal("price"));
            product.setStockQuantity(rs.getInt("stock_quantity"));
            product.setCreatedAt(
                    rs.getTimestamp("created_at") != null
                            ? rs.getTimestamp("created_at").toLocalDateTime()
                            : null
            );
            return product;
        };
    }
}
