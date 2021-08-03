package ru.javawebinar.storage;

import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.ContactsType;
import ru.javawebinar.model.Resume;
import ru.javawebinar.sql.ConnectionFactory;
import ru.javawebinar.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage{
    private SqlHelper sh;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        ConnectionFactory connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sh = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        sh.execute("DELETE FROM resume",
                preparedStatement -> {
            preparedStatement.execute();
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sh.execute("UPDATE resume SET full_name=? WHERE uuid=?",
                preparedStatement -> {
            String uuid = resume.getUuid();
            preparedStatement.setString(1, resume.getFullName());
            preparedStatement.setString(2, uuid);
            if (preparedStatement.executeUpdate() != 1) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sh.execute("" +
                        " SELECT * FROM resume r " +
                        "   LEFT JOIN contact c " +
                        "    ON r.uuid = c.resume_uuid " +
                        " WHERE r.uuid = ?",
                preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            do {
                String value = rs.getString("value");
                ContactsType type = ContactsType.valueOf((rs.getString("type")));
                r.addContact(type, value);

            } while(rs.next());
            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        sh.execute("DELETE FROM resume WHERE uuid=?",
                preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() != 1) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sh.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }

            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                for (Map.Entry<ContactsType, String> e : resume.getContacts().entrySet()) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, e.getKey().name());
                    ps.setString(3, e.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sh.transactionalExecute(conn -> {
            List<Resume> resumeList = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r " +
                                                                    "ORDER BY full_name, uuid")) {

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    String name = rs.getString("full_name");
                    Resume r = new Resume(uuid, name);
                    resumeList.add(r);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r " +
                                                                    "LEFT JOIN contact c " +
                                                                    "ON r.uuid = c.resume_uuid " +
                                                                    "WHERE r.uuid = ?")) {
                for(Resume r : resumeList) {
                    ps.setString(1, r.getUuid());
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()) {
                        String value = rs.getString("value");
                        ContactsType type = ContactsType.valueOf((rs.getString("type")));
                        r.addContact(type, value);
                    }
                }
            }
            return resumeList;
        });
    }
    
    @Override
    public int size() {
        return sh.execute("SELECT count(*) FROM resume",
                preparedStatement -> {
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }
}
