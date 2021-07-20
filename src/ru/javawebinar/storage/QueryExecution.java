package ru.javawebinar.storage;

import java.sql.Connection;
import java.sql.SQLException;

public interface QueryExecution<T> {
    T executeQuery(Connection connection) throws SQLException;
}
