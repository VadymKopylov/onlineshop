package com.kopylov.onlineshop.database.mapper;

import com.kopylov.onlineshop.entity.User;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserRowMapperTest {
    @Test
    public void testUserMapRow() throws SQLException {
        UserRowMapper userRowMapper = new UserRowMapper();

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString("email")).thenReturn("example@gmail.com");
        when(resultSet.getString("password")).thenReturn("password");
        when(resultSet.getString("salt")).thenReturn("salt");

        User actual = userRowMapper.mapRow(resultSet);

        assertEquals("example@gmail.com", actual.getEmail());
        assertEquals("password", actual.getPassword());
        assertEquals("salt", actual.getSalt());
    }

}