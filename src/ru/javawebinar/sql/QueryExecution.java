package ru.javawebinar.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface QueryExecution<T> {
    T executeQuery(Connection connection) throws SQLException;
}
