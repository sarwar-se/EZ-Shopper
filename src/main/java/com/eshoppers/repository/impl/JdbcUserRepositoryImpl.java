package com.eshoppers.repository.impl;

import com.eshoppers.annotation.JDBC;
import com.eshoppers.domain.User;
import com.eshoppers.jdbc.JDBCTemplate;
import com.eshoppers.repository.UserRepository;
import jakarta.inject.Inject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

@JDBC
public class JdbcUserRepositoryImpl implements UserRepository {

    private final static String SAVE_USER = """
            INSERT INTO user
                (username,
                password,
                version,
                date_created,
                date_last_updated,
                email,
                first_name,
                last_name)
            VALUES (?,?,?,?,?,?,?,?)
            """;

    private final static String SELECT_BY_USERNAME = """
               SELECT
                   id,
                   username,
                   password,
                   version,
                   date_created,
                   date_last_updated,
                   email,
                   first_name,
                   last_name
               FROM user WHERE username = ?
            """;

    private final static String SELECT_BY_EMAIL = """
              SELECT
                  id,
                  username,
                  password,
                  version,
                  date_created,
                  date_last_updated,
                  email,
                  first_name,
                  last_name
              FROM user WHERE email = ?
            """;

    private final JDBCTemplate jdbcTemplate;

    @Inject
    public JdbcUserRepositoryImpl(JDBCTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(User user) {
        jdbcTemplate.executeInsertQuery(SAVE_USER,
                user.getUsername(),
                user.getPassword(),
                0L,
                Timestamp.valueOf(user.getDateCreated()),
                Timestamp.valueOf(user.getDateLastUpdated()),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    @Override
    public Optional<User> findByUsername(String username) {
        var users = jdbcTemplate.queryForObject(SELECT_BY_USERNAME, username, this::extractUser);

        return users.size() > 0 ? Optional.of(users.get(0)) : Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        var users = jdbcTemplate.queryForObject(SELECT_BY_EMAIL, email, this::extractUser);

        return users.size() > 0 ? Optional.of(users.get(0)) : Optional.empty();
    }

    private User extractUser(ResultSet resultSet) throws SQLException {
        var user = new User();

        user.setId(resultSet.getLong("id"));
        user.setVersion(resultSet.getLong("version"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setDateCreated(resultSet.getTimestamp("date_created").toLocalDateTime());
        user.setDateLastUpdated(resultSet.getTimestamp("date_last_updated").toLocalDateTime());
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setEmail(resultSet.getString("email"));

        return user;
    }
}
