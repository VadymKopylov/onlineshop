package com.kopylov.onlineshop.database.mapper;

import com.kopylov.onlineshop.entity.User;
import org.apache.commons.text.StringEscapeUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {
    public User mapRow(ResultSet resultSet) throws SQLException {
        String email = StringEscapeUtils.escapeHtml4(resultSet.getString("email"));
        String password = StringEscapeUtils.escapeHtml4(resultSet.getString("password"));
        String salt = StringEscapeUtils.escapeHtml4(resultSet.getString("salt"));
        return new User(email, password, salt);
    }
}
