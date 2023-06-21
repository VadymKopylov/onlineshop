package com.kopylov.onlineshop.dao.jdbc;

import com.kopylov.onlineshop.dao.jdbc.mapper.ProductRowMapper;
import com.kopylov.onlineshop.back.entity.Product;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class JdbcProductsDao implements ProductsDao {
    private final ConnectionFactory connectionFactory;
    private final ProductRowMapper productRowMapper = new ProductRowMapper();

    private static final String SELECT_PRODUCT_BY_NAME_SQL = """
            SELECT * FROM products WHERE name LIKE ?
            """;
    private static final String INSERT_PRODUCT_SQL = """
            INSERT INTO products (name, price, creation_date) VALUES (?, ?, ?)
            """;
    private static final String UPDATE_PRODUCT_SQL = """
            UPDATE products SET name = ?, price = ?, creation_date = ? WHERE id = ?;
            """;
    private static final String SELECT_PRODUCT_SQL = """
            SELECT id, name, price, creation_date FROM products WHERE id = ?;
            """;
    private static final String DELETE_PRODUCT_SQL = """
            DELETE FROM products WHERE id = ?;
            """;
    private static final String SELECT_ALL_PRODUCTS_SQL = """
            SELECT id, name, price, creation_date FROM products;
            """;

    @Override
    public void add(Product product) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setTimestamp(3, Timestamp.valueOf(product.getCreationDate()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Exception with insert Product", e);
        }
    }

    @Override
    public List<Product> findAll() {
        try (Connection connection = connectionFactory.getConnection();
             ResultSet resultSet = connection.createStatement().executeQuery(SELECT_ALL_PRODUCTS_SQL)) {
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = productRowMapper.mapRow(resultSet);
                products.add(product);
            }
            if (products.isEmpty()) {
                return null;
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Exception with select all Product", e);
        }
    }

    @Override
    public Product findById(int id) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCT_SQL)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }
                return productRowMapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error with select Product by id", e);
        }
    }

    @Override
    public List<Product> findByName(String name) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCT_BY_NAME_SQL)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Product> products = new ArrayList<>();
                while (resultSet.next()) {
                    Product product = productRowMapper.mapRow(resultSet);
                    products.add(product);
                }
                return products;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Exception with select Product by id", e);
        }
    }

    @Override
    public void update(Product product) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT_SQL)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setTimestamp(3, Timestamp.valueOf(product.getCreationDate()));
            statement.setInt(4, product.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Exception with update Product", e);
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Exception with delete Product by id", e);
        }
    }
}

