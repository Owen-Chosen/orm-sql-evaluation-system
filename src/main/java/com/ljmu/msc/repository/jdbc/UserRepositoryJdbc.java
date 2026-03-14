package com.ljmu.msc.repository.jdbc;

import com.ljmu.msc.domain.Order;
import com.ljmu.msc.domain.User;
import com.ljmu.msc.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class UserRepositoryJdbc implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User save(User user) {
        String sql = """
            INSERT INTO users(email, name, created_at)
            VALUES (?, ?, ?)
            RETURNING id
        """;
        Long id = jdbcTemplate.queryForObject(
                sql,
                Long.class,
                user.getEmail(),
                user.getName(),
                LocalDateTime.now()
        );
        user.setId(id);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> result = jdbcTemplate.query(sql, orderRowMapper(), id);

        return result.stream().findFirst();
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query( sql, orderRowMapper());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<User> orderRowMapper() {
        return (resultSet, rowNum) -> {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setEmail(resultSet.getString("email"));
            user.setName(resultSet.getString("name"));
            user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            return user;
        };
    }
}
