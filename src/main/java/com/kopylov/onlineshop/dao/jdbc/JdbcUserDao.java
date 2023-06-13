package com.kopylov.onlineshop.dao.jdbc;

import com.kopylov.onlineshop.dao.UserDao;
import com.kopylov.onlineshop.dao.jdbc.mapper.UserRowMapper;
import com.kopylov.onlineshop.entity.User;

import java.sql.*;

public class JdbcUserDao implements UserDao {
    private final ConnectionFactory connectionFactory;

    private static final String INSERT_SAVE_USER_SQL = """
            INSERT INTO users (email,password,salt) VALUES (?, ?, ?)
            """;
    private static final String SELECT_USER_SQL = """
            SELECT email, password, salt FROM users WHERE email = ?;
            """;
    private static final String SELECT_USER_EXIST_SQL = """
            SELECT COUNT(*) FROM users WHERE email = ?;
            """;

    public JdbcUserDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void addToDataBase(User user) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SAVE_USER_SQL)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getSalt());
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
