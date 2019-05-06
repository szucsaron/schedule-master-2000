package com.codecool.web.dao.simple;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AbstractDao implements AutoCloseable {
    protected final Connection connection;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

    int fetchGeneratedId(PreparedStatement statement) throws SQLException {
        int id;
        try (ResultSet resultSet = statement.getGeneratedKeys()) {
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            } else {
                connection.rollback();
                throw new SQLException("Expected 1 result");
            }
        }
        connection.commit();
        return id;
    }

    public void close() throws SQLException {
        connection.close();
    }
}
