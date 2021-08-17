package ru.javawebinar.storage;

import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.*;
import ru.javawebinar.sql.SqlHelper;
import ru.javawebinar.sql.SqlTransaction;
import ru.javawebinar.util.JsonParser;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage{
    public final SqlHelper sh;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("No driver found for PostgreSQL database", e);
        }
        sh = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
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
        sh.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? " +
                    "WHERE uuid = ?")) {
                String uuid = resume.getUuid();
                ps.setString(1, resume.getFullName());
                ps.setString(2, uuid);
                SqlStorage.this.checkUpdateResume(ps, uuid);
            }
            deleteResumeFromTable("contact", conn, resume);
            deleteResumeFromTable("section", conn, resume);
            insertContentToDB(conn, resume);

            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sh.transactionalExecute(conn -> {
            Resume r;
            try (PreparedStatement ps = conn.prepareStatement(makeQueryJoin("contact"))) {
                ResultSet rs = getExecuteResult(ps, uuid);
                r = new Resume(uuid, rs.getString("full_name"));
                do {
                    addContactsToResume(rs, r);
                } while(rs.next());
            }

            try (PreparedStatement ps = conn.prepareStatement(makeQueryJoin("section"))) {
                ResultSet rs = getExecuteResult(ps, uuid);
                do {
                    addSectionsToResume(r, rs);
                } while(rs.next());
            }
            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        sh.execute("DELETE FROM resume WHERE uuid=?",
                preparedStatement -> {
            preparedStatement.setString(1, uuid);
            checkUpdateResume(preparedStatement, uuid);
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
            insertContentToDB(conn, resume);

            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sh.transactionalExecute(conn -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "   SELECT * FROM resume r\n" +
                    "ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    Resume resume = map.get(uuid);
                    if (resume == null) {
                        map.put(uuid, new Resume(uuid, rs.getString("full_name")));
                    }
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("" +
                    "   SELECT * FROM contact c")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    Resume resume = map.get(uuid);
                    addContactsToResume(rs, resume);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("" +
                    "   SELECT * FROM section s")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    Resume resume = map.get(uuid);
                    addSectionsToResume(resume, rs);
                }
            }
            return new ArrayList<>(map.values());
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

    private void deleteResumeFromTable(String tableName, Connection conn, Resume resume) throws SQLException {
        String query = "DELETE FROM " + tableName +
                        " WHERE resume_uuid = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void checkUpdateResume(PreparedStatement ps, String uuid) throws SQLException {
        if (ps.executeUpdate() != 1) {
            throw new NotExistStorageException(uuid);
        }
    }

    private void insertContentToDB(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO contact (resume_uuid, value, type) VALUES (?,?,?)")) {
            for (Map.Entry<ContactsType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getValue());
                ps.setString(3, e.getKey().name());

                ps.addBatch();
            }
            ps.executeBatch();
        }

        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO section (resume_uuid, value, type) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : resume.getSectionsAll().entrySet()) {
                ps.setString(1, resume.getUuid());
                SectionType sectionType = e.getKey();
                AbstractSection section = e.getValue();
                ps.setString(2, JsonParser.write(section, AbstractSection.class));
                ps.setString(3, sectionType.name());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private String makeQueryJoin(String tableName) {
        return "" +
                " SELECT * FROM resume r " +
                "   LEFT JOIN " +
                tableName + " c " +
                "    ON r.uuid = c.resume_uuid " +
                " WHERE r.uuid = ?";
    }

    private ResultSet getExecuteResult(PreparedStatement ps, String uuid) throws SQLException {
        ps.setString(1, uuid);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            throw new NotExistStorageException(uuid);
        }
        return rs;
    }

    private void addContactsToResume(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            ContactsType type = ContactsType.valueOf((rs.getString("type")));
            r.addContact(type, value);
        }
    }

    private void addSectionsToResume(Resume r, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            SectionType secType = SectionType.valueOf(rs.getString("type"));
            r.addSection(secType, JsonParser.read(value, AbstractSection.class));
        }
    }
}
