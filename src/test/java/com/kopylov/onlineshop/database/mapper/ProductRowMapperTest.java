package com.kopylov.onlineshop.database.mapper;

import com.kopylov.onlineshop.entity.Product;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductRowMapperTest {
    @Test
    public void testProductMapRow() throws SQLException {
        ProductRowMapper productRowMapper = new ProductRowMapper();
        LocalDateTime localDateTime = LocalDateTime.of(2022,10,10,10,10,10);
        Timestamp timestamp = Timestamp.valueOf(localDateTime);

        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.getInt("id")).thenReturn(5);
        when(resultSetMock.getString("name")).thenReturn("Car");
        when(resultSetMock.getDouble("price")).thenReturn(1000.0);
        when(resultSetMock.getTimestamp("creationDate")).thenReturn(timestamp);

        Product actual = productRowMapper.mapRow(resultSetMock);

        assertEquals(5,actual.getId());
        assertEquals("Car",actual.getName());
        assertEquals(1000.0,actual.getPrice());
        assertEquals(localDateTime,actual.getCreationDate());
    }
}