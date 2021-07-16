package ru.javawebinar.storage;

import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;
import ru.javawebinar.sql.ConnectionFactory;

import java.beans.PropertyEditorSupport;
import java.sql.*;
import java.util.List;

public class SqlStorage implements Storage{
    private final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM resume");
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
    @Override
    public void update(Resume resume) {

    }

    @Override
    public Resume get(String uuid) {
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid=?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            return r;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void delete(String uuid) {
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM resume WHERE uuid=?");
            ps.setString(1, uuid);
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void save(Resume resume) {
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)");
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume");
            ResultSet rs = ps.executeQuery();
            List<Resume> resumeList = null;

            while (rs.next()) {
                String uuid = rs.getString("uuid");
                String fullName = rs.getString("full_name");
                //Assuming you have a user object
                Resume r = new Resume(uuid, fullName);
                resumeList.add(r);
            }
            return resumeList;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public int size() {
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT count(*) FROM resume");
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
