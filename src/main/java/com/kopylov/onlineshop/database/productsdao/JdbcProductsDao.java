package com.kopylov.onlineshop.database.productsdao;

import com.kopylov.onlineshop.database.mapper.ProductRowMapper;
import com.kopylov.onlineshop.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductsDao implements ProductsDao {

    private static final String URL = "jdbc:postgresql://localhost:5432/shopdatabase";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "mysecretpassword";

    private static final String INSERT_SAVE_PRODUCT_SQL = """
            INSERT INTO Products (name,price,creationDate) VALUES (?, ?, ?)
            """;
    private static final String UPDATE_PRODUCT_SQL = """
            UPDATE products SET name = ?, price = ?, creationDate = ? WHERE ID = ?;
            """;
    private static final String SELECT_PRODUCT_SQL = """
            SELECT name, price, creationDate FROM products WHERE ID = ?;
            """;
    private static final String DELETE_PRODUCT_SQL = """
            DELETE FROM Products WHERE ID = ?;
            """;
    private static final String CLEAR_TABLE_SQL = """
            DELETE FROM Products;
            """;
    private static final String SELECT_ALL_SQL = """
            SELECT id, name, price, creationDate FROM Products;
            """;

    @Override
    public void addToDataBase(Product product) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SAVE_PRODUCT_SQL)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setTimestamp(3, Timestamp.valueOf(product.getCreationDate()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error with insert Product", e);
        }
    }

    @Override
    public List<Product> findAll() {
        try (Connection connection = getConnection();
             ResultSet resultSet = connection.createStatement().executeQuery(SELECT_ALL_SQL)) {
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = new ProductRowMapper().mapRow(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error with select all Product", e);
        }
    }

    @Override
    public void update(Product product) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT_SQL)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setTimestamp(3, Timestamp.valueOf(product.getCreationDate()));
            statement.setInt(4, product.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error with update Product", e);
        }
    }

    @Override
    public List<Product> findById(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCT_SQL)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Product> products = new ArrayList<>();
                if (resultSet.next()) {
                    Product product = new ProductRowMapper().mapRow(resultSet, id);
                    products.add(product);
                    return products;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error with select Product by id", e);
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error with delete Product by id", e);
        }
    }

    @Override
    public void deleteAll() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(CLEAR_TABLE_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error with delete all Products", e);
        }
    }

    @Override
    public int count() {
        return findAll().size();
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error with connection to db", e);
        }
    }
}

