package com.kopylov.onlineshop.dao.jdbc;

import com.kopylov.onlineshop.entity.User;
import com.kopylov.onlineshop.dao.jdbc.mapper.UserRowMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
@AllArgsConstructor
public class JdbcUserDao implements UserDao {

    private DataSource dataSource;

    private static final String SAVE_USER_SQL = """
            INSERT INTO users (role_name,email,password,salt) VALUES (?, ?, ?, ?)
            """;
    private static final String SELECT_USER_SQL = """
            SELECT role_name, email, password, salt FROM users WHERE email = ?;
            """;

    @Override
    public void add(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_USER_SQL)) {
            statement.setString(1, String.valueOf(user.getRole()));
            statement.setString(2, user.getCredentials().getEmail());
            statement.setString(3, user.getCredentials().getPassword());
            statement.setString(4, user.getSalt());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Exception with insert User", e);
        }
    }

    @Override
    public User findByEmail(String email) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_SQL)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new UserRowMapper().mapRow(resultSet);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Exception with select User by email", e);
        }
    }
}
