package com.ljmu.msc.repository.jdbc;

import com.ljmu.msc.domain.Order;
import com.ljmu.msc.repository.OrderRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class OrderRepositoryJdbc implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Order> findById(Long id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        List<Order> result = jdbcTemplate.query(sql, orderRowMapper(), id);
        return result.stream().findFirst();
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        return jdbcTemplate.query(sql, orderRowMapper(), userId);
    }

    @Override
    public List<Order> findOrdersBetween(LocalDateTime start, LocalDateTime end) {
        String sql = """
            SELECT * FROM orders
            WHERE created_at BETWEEN ? AND ?
        """;
        return jdbcTemplate.query(sql, orderRowMapper(), start, end);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Order save(Order order) {
        String sql = """
            INSERT INTO orders (user_id, total_amount, status, created_at)
            VALUES (?, ?, ?, ?)
            RETURNING id
        """;
        Long generatedId = jdbcTemplate.queryForObject(
                sql,
                Long.class,
                order.getUserId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt() != null ? order.getCreatedAt() : LocalDateTime.now()
        );

        order.setId(generatedId);
        return order;
    }

    private RowMapper<Order> orderRowMapper() {
        return (rs, rowNum) -> {
            Order order = new Order();
            order.setId(rs.getLong("id"));
            order.setUserId(rs.getLong("user_id"));
            order.setTotalAmount(rs.getBigDecimal("total_amount"));
            order.setStatus(rs.getString("status"));
            order.setCreatedAt(
                    rs.getTimestamp("created_at").toLocalDateTime()
            );
            return order;
        };
    }
}
