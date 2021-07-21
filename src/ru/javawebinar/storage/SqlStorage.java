package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;
import ru.javawebinar.sql.ConnectionFactory;
import ru.javawebinar.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage{
    private final ConnectionFactory connectionFactory;

    private SqlHelper sh;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sh = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        sh.execute(preparedStatement -> {
            preparedStatement.execute();
            return null;
        }, "DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        sh.execute(preparedStatement -> {
            preparedStatement.setString(1, resume.getFullName());
            preparedStatement.setString(2, resume.getUuid());
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        }, "UPDATE resume SET full_name=? WHERE uuid=? RETURNING full_name");
    }

    @Override
    public Resume get(String uuid) {
        return sh.execute(preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            return r;
        }, "SELECT * FROM resume WHERE uuid=?");
    }

    @Override
    public void delete(String uuid) {
        sh.execute(preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (!preparedStatement.execute()) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        }, "DELETE FROM resume WHERE uuid=?");
    }

    @Override
    public void save(Resume resume) {
        sh.execute(preparedStatement -> {
            preparedStatement.setString(1, resume.getUuid());
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                throw new ExistStorageException(resume.getUuid());
            } else {
                sh.execute(preparedStatement1 -> {
                    preparedStatement1.setString(1, resume.getUuid());
                    preparedStatement1.setString(2, resume.getFullName());
                    preparedStatement1.execute();
                    return null;
                    }, "INSERT INTO resume (uuid, full_name) VALUES (?,?)");
            }
            return null;
        }, "SELECT * FROM resume WHERE uuid=?");
    }

    @Override
    public List<Resume> getAllSorted() {
        return sh.execute(preparedStatement -> {
            ResultSet rs = preparedStatement.executeQuery();
            List<Resume> resumeList = new ArrayList<>();

            while (rs.next()) {
                String uuid = rs.getString("uuid");
                String fullName = rs.getString("full_name");
                Resume r = new Resume(uuid, fullName);
                resumeList.add(r);
            }
            return resumeList;
        }, "SELECT * FROM resume");
    }

    @Override
    public int size() {
        return sh.execute(preparedStatement -> {
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getInt(1);
        }, "SELECT count(*) FROM resume");
    }
}
