package ru.javawebinar.sql;

import org.postgresql.util.PSQLException;
import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.StorageException;

import java.sql.SQLException;

public class ExceptionUtil {
    private ExceptionUtil() {

    }

    public static StorageException convertException(SQLException e) {
        if (e  instanceof PSQLException) {
            if (e.getSQLState().equals("23505")) {
                return new ExistStorageException(e);
            }
        }
        return new StorageException(e);
    }
}
