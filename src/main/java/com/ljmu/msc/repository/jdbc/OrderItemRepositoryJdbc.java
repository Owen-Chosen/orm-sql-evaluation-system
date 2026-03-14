package com.ljmu.msc.repository.jdbc;

import com.ljmu.msc.domain.OrderItem;
import com.ljmu.msc.repository.OrderItemRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("jdbc")
public class OrderItemRepositoryJdbc implements OrderItemRepository {


    private final JdbcTemplate jdbcTemplate;

    public OrderItemRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public OrderItem save(OrderItem item) {
        String sql = """
            INSERT INTO order_items(order_id, product_id, quantity, unit_price)
            VALUES (?, ?, ?, ?)
            RETURNING id
        """;
        Long generatedId = jdbcTemplate.queryForObject(
                sql,
                Long.class,
                item.getOrderId(),
                item.getProductId(),
                item.getQuantity(),
                item.getUnitPrice()
        );
        item.setId(generatedId);
        return item;
    }

    @Override
    public List<OrderItem> findByOrderId(Long orderId) {

        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        return jdbcTemplate.query(sql, orderItemRowMapper(), orderId);
    }

    @Override
    public void deleteByOrderId(Long orderId) {
        String sql = "DELETE FROM order_items WHERE order_id = ?";
        jdbcTemplate.update(sql, orderId);
    }

    private RowMapper<OrderItem> orderItemRowMapper() {
        return (rs, rowNum) -> {
            OrderItem item = new OrderItem();
            item.setId(rs.getLong("id"));
            item.setOrderId(rs.getLong("order_id"));
            item.setProductId(rs.getLong("product_id"));
            item.setQuantity(rs.getInt("quantity"));
            item.setUnitPrice(rs.getBigDecimal("unit_price"));
            return item;
        };
    }
}
