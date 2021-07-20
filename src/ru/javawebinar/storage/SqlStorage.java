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
        sh.execute(connection -> {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM resume");
            ps.execute();
            return null;
        });
    }
    @Override
    public void update(Resume resume) {
        sh.execute(connection -> {
            PreparedStatement ps = connection.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=? RETURNING full_name");
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sh.execute(connection -> {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume WHERE uuid=?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        sh.execute(connection -> {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM resume WHERE uuid=?");
            ps.setString(1, uuid);
            if (!ps.execute()) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sh.execute(connection -> {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume WHERE uuid=?");
            ps.setString(1, resume.getUuid());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                throw new ExistStorageException(resume.getUuid());
            } else {
                ps = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)");
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sh.execute(connection -> {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume");
            ResultSet rs = ps.executeQuery();
            List<Resume> resumeList = new ArrayList<>();

            while (rs.next()) {
                String uuid = rs.getString("uuid");
                String fullName = rs.getString("full_name");
                Resume r = new Resume(uuid, fullName);
                resumeList.add(r);
            }
            return resumeList;
        });
    }

    @Override
    public int size() {
        return sh.execute(connection -> {
            PreparedStatement ps = connection.prepareStatement("SELECT count(*) FROM resume");
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }
}
