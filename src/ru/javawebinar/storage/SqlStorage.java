package ru.javawebinar.storage;

import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.*;
import ru.javawebinar.sql.ConnectionFactory;
import ru.javawebinar.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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
        sh.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? " +
                                                                    "WHERE uuid = ?")) {
                String uuid = resume.getUuid();
                ps.setString(1, resume.getFullName());
                ps.setString(2, uuid);
                checkNotExistStorageException(ps, uuid);
            }

            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact " +
                                                                    "WHERE resume_uuid = ?")) {
                ps.setString(1, resume.getUuid());
                ps.execute();
            }

            insertContacts(conn, resume);
            insertTextSection(SectionType.PERSONAL, conn, resume);
            insertTextSection(SectionType.POSITION, conn, resume);
            insertListSection(SectionType.QUALIFICATION, conn, resume);
            insertListSection(SectionType.ACHIEVEMENT, conn, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sh.transactionalExecute(conn -> {
            Resume r;
            try (PreparedStatement ps = conn.prepareStatement(makeQueryJoin("contact"))) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, rs.getString("full_name"));
                do {
                    addContactsToResume(rs, r);
                } while(rs.next());
            }

            try (PreparedStatement ps = conn.prepareStatement(makeQueryJoin("personal"))) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                do {
                    addTextSectionToResume(SectionType.PERSONAL, rs, r);
                } while(rs.next());
            }

            try (PreparedStatement ps = conn.prepareStatement(makeQueryJoin("position"))) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                do {
                    addTextSectionToResume(SectionType.POSITION, rs, r);
                } while(rs.next());
            }

            try (PreparedStatement ps = conn.prepareStatement(makeQueryJoin("qualification"))) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                List<String> lSection;
                String str = rs.getString("value");
                if (str != null) {
                    lSection = Arrays.asList(str.split("\n"));
                    r.addSection(SectionType.QUALIFICATION, new ListSection(lSection));
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(makeQueryJoin("achievement"))) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                List<String> lSection;
                String str = rs.getString("value");
                if (str != null) {
                    lSection = Arrays.asList(str.split("\n"));
                    r.addSection(SectionType.ACHIEVEMENT, new ListSection(lSection));
                }
            }

            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        sh.execute("DELETE FROM resume WHERE uuid=?",
                preparedStatement -> {
            preparedStatement.setString(1, uuid);
            checkNotExistStorageException(preparedStatement, uuid);
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

            insertContacts(conn, resume);
            insertTextSection(SectionType.PERSONAL, conn, resume);
            insertTextSection(SectionType.POSITION, conn, resume);
            insertListSection(SectionType.QUALIFICATION, conn, resume);
            insertListSection(SectionType.ACHIEVEMENT, conn, resume);
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

            try (PreparedStatement ps = conn.prepareStatement(makeQueryJoin("contact"))) {
                for (Resume r : resumeList) {
                    ps.setString(1, r.getUuid());
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        addContactsToResume(rs, r);
                    }
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(makeQueryJoin("personal"))) {
                for (Resume r : resumeList) {
                    ps.setString(1, r.getUuid());
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        addTextSectionToResume(SectionType.PERSONAL, rs, r);
                    }
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(makeQueryJoin("position"))) {
                for (Resume r : resumeList) {
                    ps.setString(1, r.getUuid());
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        addTextSectionToResume(SectionType.POSITION, rs, r);
                    }
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(makeQueryJoin("qualification"))) {
                for (Resume r : resumeList) {
                    ps.setString(1, r.getUuid());
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        addListSectionToResume(SectionType.QUALIFICATION, rs, r);
                    }
                }
            }


            try (PreparedStatement ps = conn.prepareStatement(makeQueryJoin("achievement"))) {
                for (Resume r : resumeList) {
                    ps.setString(1, r.getUuid());
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        addListSectionToResume(SectionType.ACHIEVEMENT, rs, r);
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

    private void checkNotExistStorageException(PreparedStatement ps, String uuid) throws SQLException {
        if (ps.executeUpdate() != 1) {
            throw new NotExistStorageException(uuid);
        }
    }

    private void insertContacts(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO contact (resume_uuid, value, type) VALUES (?,?,?)")) {
            for (Map.Entry<ContactsType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getValue());
                ps.setString(3, e.getKey().name());

                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContactsToResume(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            ContactsType type = ContactsType.valueOf((rs.getString("type")));
            r.addContact(type, value);
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

    private void insertTextSection(SectionType secType, Connection connection, Resume resume) throws SQLException {
        TextSection textSection = ((TextSection)resume.getSection(secType));
        if (textSection != null) {
            switch(secType) {
                case PERSONAL:
                    try (PreparedStatement ps = connection.prepareStatement("INSERT INTO personal (resume_uuid, value) VALUES (?,?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, textSection.toString());
                        ps.execute();
                    }
                    break;
                case POSITION:
                    try (PreparedStatement ps = connection.prepareStatement("INSERT INTO position (resume_uuid, value) VALUES (?,?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, textSection.toString());
                        ps.execute();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void addTextSectionToResume(SectionType secType, ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            switch (secType) {
                case POSITION:
                    r.addSection(SectionType.POSITION, new TextSection(value));
                    break;
                case PERSONAL:
                    r.addSection(SectionType.PERSONAL, new TextSection(value));
                    break;
                default:
                    break;
            }
        }
    }

    private void addListSectionToResume(SectionType secType, ResultSet rs, Resume r) throws SQLException {
        List<String> lSection;
        String str = rs.getString("value");
        if (str != null) {
            lSection = Arrays.asList(str.split("\n"));
            switch (secType) {
                case QUALIFICATION:
                    r.addSection(SectionType.QUALIFICATION, new ListSection(lSection));
                    break;
                case ACHIEVEMENT:
                    r.addSection(SectionType.ACHIEVEMENT, new ListSection(lSection));
                    break;
                default:
                    break;
            }
        }
    }

    private void insertListSection(SectionType secType, Connection conn, Resume resume) throws SQLException {
        ListSection listSection = (ListSection)resume.getSection(secType);
        if (listSection != null) {
            switch(secType) {
                case QUALIFICATION:
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO qualification (resume_uuid, value) VALUES (?,?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, listSection.toString());
                        ps.execute();
                    }
                    break;
                case ACHIEVEMENT:
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO achievement (resume_uuid, value) VALUES (?,?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, listSection.toString());
                        ps.execute();
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
