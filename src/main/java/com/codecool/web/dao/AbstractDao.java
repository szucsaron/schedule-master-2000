package com.codecool.web.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class AbstractDao implements AutoCloseable {
    protected final Connection connection;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

    public void close() throws SQLException {
        connection.close();
    }
}
