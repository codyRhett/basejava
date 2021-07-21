package ru.javawebinar.sql;

import ru.javawebinar.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(QueryExecution<T> boc, String query) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            return (T)boc.executeQuery(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
