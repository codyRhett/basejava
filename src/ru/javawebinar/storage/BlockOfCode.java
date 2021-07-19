package ru.javawebinar.storage;

import java.sql.Connection;
import java.sql.SQLException;

public interface BlockOfCode {
    void executeCode(Connection connection) throws SQLException;
}
