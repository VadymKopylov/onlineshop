package com.kopylov.onlineshop.dao.jdbc.mapper;

import com.kopylov.onlineshop.back.entity.Credentials;
import com.kopylov.onlineshop.back.entity.User;
import com.kopylov.onlineshop.back.entity.UserRole;
import org.apache.commons.text.StringEscapeUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {

    public User mapRow(ResultSet resultSet) throws SQLException {
        UserRole userRole = UserRole.valueOf(resultSet.getString("role_name"));
        String email = StringEscapeUtils.escapeHtml4(resultSet.getString("email"));
        String password = StringEscapeUtils.escapeHtml4(resultSet.getString("password"));
        String salt = StringEscapeUtils.escapeHtml4(resultSet.getString("salt"));
        Credentials credentials = new Credentials(email, password);
        return new User()
                .setRole(userRole)
                .setCredentials(credentials)
                .setSalt(salt);
    }
}
