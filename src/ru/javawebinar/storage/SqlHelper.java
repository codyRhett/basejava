package ru.javawebinar.storage;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.sql.ConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(QueryExecution <T> boc) {
        try (Connection connection = connectionFactory.getConnection()) {
            return (T)boc.executeQuery(connection);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
