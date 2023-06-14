package com.kopylov.onlineshop.dao.jdbc;

import com.kopylov.onlineshop.dao.UserDao;
import com.kopylov.onlineshop.dao.jdbc.mapper.UserRowMapper;
import com.kopylov.onlineshop.entity.User;
import lombok.RequiredArgsConstructor;

import java.sql.*;

@RequiredArgsConstructor
public class JdbcUserDao implements UserDao {
    private final ConnectionFactory connectionFactory;

    private static final String SAVE_USER_SQL = """
            INSERT INTO users (role_name,email,password,salt) VALUES (?, ?, ?, ?)
            """;
    private static final String SELECT_USER_SQL = """
            SELECT role_name, email, password, salt FROM users WHERE email = ?;
            """;
    private static final String SELECT_USER_EXIST_SQL = """
            SELECT COUNT(*) FROM users WHERE email = ?;
            """;

    @Override
    public void addToDataBase(User user) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_USER_SQL)) {
            statement.setString(1, String.valueOf(user.getRole()));
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getSalt());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error with insert User", e);
        }
    }

    @Override
    public User findByEmail(String email) {
        try (Connection connection = connectionFactory.getConnection();
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
            e.printStackTrace();
            throw new RuntimeException("Error with select User by email", e);
        }
    }

    public boolean isExist(String email) {
        boolean userExists = false;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_EXIST_SQL)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    if (count > 0) {
                        userExists = true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error with select User by email", e);
        }
        return userExists;
    }
}
