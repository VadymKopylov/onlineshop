package com.kopylov.onlineshop.dao.jdbc;

import com.kopylov.onlineshop.entity.Product;
import com.kopylov.onlineshop.dao.jdbc.mapper.ProductRowMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@AllArgsConstructor
public class JdbcProductsDao implements ProductsDao {

    private DataSource dataSource;
    private final ProductRowMapper productRowMapper = new ProductRowMapper();

    private static final String INSERT_PRODUCT_SQL = """
            INSERT INTO products (id, name, price, creation_date) VALUES (?, ?, ?, ?)
            """;
    private static final String UPDATE_PRODUCT_SQL = """
            UPDATE products SET name = ?, price = ?, creation_date = ? WHERE id = ?;
            """;
    private static final String DELETE_PRODUCT_SQL = """
            DELETE FROM products WHERE id = ?;
            """;
    private static final String SELECT_ALL_PRODUCTS_SQL = """
            SELECT id, name, price, creation_date FROM products;
            """;

    @Override
    public void add(Product product) {
        log.debug("Connecting to db");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {
            statement.setString(1, product.getId());
            statement.setString(2, product.getName());
            statement.setDouble(3, product.getPrice());
            statement.setTimestamp(4, Timestamp.valueOf(product.getCreationDate()));
            statement.executeUpdate();
            log.debug("Product was added: [name={}]", product.getName());
        } catch (SQLException e) {
            throw new RuntimeException("Exception with insert Product", e);
        }
    }

    @Override
    public List<Product> findAll() {
        log.debug("Connecting to db");
        try (Connection connection = dataSource.getConnection();
             ResultSet resultSet = connection.createStatement().executeQuery(SELECT_ALL_PRODUCTS_SQL)) {
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = productRowMapper.mapRow(resultSet);
                products.add(product);
            }
            log.debug("Found products. [size={}]", products.size());
            return products;
        } catch (SQLException e) {
            log.error("Exception while finding all products. [sql={}]", SELECT_ALL_PRODUCTS_SQL);
            throw new RuntimeException("Exception with select all Product", e);
        }
    }

    @Override
    public void update(Product product) {
        log.debug("Connecting to db");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT_SQL)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setTimestamp(3, Timestamp.valueOf(product.getCreationDate()));
            statement.setString(4, product.getId());
            statement.executeUpdate();
            log.debug("Product was updated successfully. [name={}]", product.getName());
        } catch (SQLException e) {
            throw new RuntimeException("Exception with update Product", e);
        }
    }

    @Override
    public void deleteById(String id) {
        log.debug("Connecting to db");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_SQL)) {
            statement.setString(1, id);
            statement.executeUpdate();
            log.debug("Product was deleted successfully. [id={}]", id);
        } catch (SQLException e) {
            throw new RuntimeException("Exception with delete Product by id", e);
        }
    }
}

