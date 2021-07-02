package ru.javawebinar.storage.strategy;

import ru.javawebinar.model.*;
import ru.javawebinar.util.DateUtil;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamStrategy implements Strategy {
    @Override
    public void doWrite(Resume resume, OutputStream file) throws IOException {
        try(DataOutputStream dos = new DataOutputStream(file)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactsType, String> contacts = resume.getContacts();

            writeWithException(contacts.entrySet(), dos, new WriteInterface<Map.Entry<ContactsType, String>>() {
                @Override
                public void doWriteData(Map.Entry<ContactsType, String> contactsWrite) throws IOException {
                    dos.writeUTF(contactsWrite.getKey().name());
                    dos.writeUTF(contactsWrite.getValue());
                }
            });

            Map<SectionType, AbstractSection> sections = resume.getSectionsAll();
            writeWithException(sections.entrySet(), dos, sectionWrite -> {
                dos.writeUTF(sectionWrite.getKey().name());
                SectionType sectionWriteKey = sectionWrite.getKey();
                switch (sectionWriteKey) {
                    case PERSONAL:
                    case POSITION:
                        dos.writeUTF(String.valueOf(sectionWrite.getValue()));
                        break;

                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        List<String> lString = ((ListSection)resume
                                .getSection(sectionWriteKey))
                                .getItems();

                        writeWithException(lString, dos, dos::writeUTF);
                        break;

                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = ((OrganizationSection)(resume
                                .getSection(sectionWriteKey)))
                                .getOrganizations();

                        writeWithException(organizations, dos, organizationWrite -> {
                            Link homePage = organizationWrite.getHomePage();
                            dos.writeUTF(homePage.getName());

                            String homeUrl = homePage.getUrl();
                            dos.writeUTF((homeUrl == null) ? "" : homeUrl);

                            List<Organization.Position> orgPos = organizationWrite.getPositions();
                            writeWithException(orgPos, dos, position -> {
                                writeDate(position, dos);
                                dos.writeUTF(position.getTitle());

                                String description = position.getDescription();
                                dos.writeUTF((description == null) ? "" : description);
                            });
                        });
                        break;
                    }
            });
        }
    }

    @Override
    public Resume doRead(InputStream file) throws IOException {
        try(DataInputStream dis = new DataInputStream(file)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            readWithException(dis, () -> {
                resume.addContact(ContactsType.valueOf(dis.readUTF()), dis.readUTF());
            });

            // Read sections
            readWithException(dis, () -> {
                SectionType secKey = SectionType.valueOf(dis.readUTF());
                switch (secKey) {
                    case PERSONAL:
                    case POSITION:
                        resume.addSection(secKey, new TextSection(dis.readUTF()));
                        break;

                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        List<String> lString = new ArrayList<>();
                        readWithException(dis, () -> {
                            lString.add(dis.readUTF());
                        });

                        resume.addSection(secKey, new ListSection(lString));
                        break;

                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> orgs = new ArrayList<>();
                        readWithException(dis, () -> {
                            String name = dis.readUTF();
                            String homeUrl = dis.readUTF();

                            List<Organization.Position> orgPos = new ArrayList<>();

                            readWithException(dis, () -> {
                                LocalDate startDate = readDate(dis);
                                LocalDate endDate = readDate(dis);

                                String title = dis.readUTF();
                                String description = dis.readUTF();

                                orgPos.add(new Organization.Position(title,
                                        (description.equals("")) ? null : description, startDate, endDate));
                            });
                            orgs.add(new Organization(name, (homeUrl.equals("")) ? null : homeUrl, orgPos));
                        });
                        resume.addSection(secKey, new OrganizationSection(orgs));
                        break;

                    default:
                        break;
                }
            });
            return resume;
        }
    }

    private <V> void writeWithException( Collection<V> collection,
                                         DataOutputStream dos,
                                         WriteInterface<V> wInt) throws IOException{
        dos.writeInt(collection.size());
        for(V val : collection) {
            wInt.doWriteData(val);
        }
    }

    private void readWithException( DataInputStream dis,
                                    ReadInterface rInt) throws IOException{
        int size = dis.readInt();
        for(int i = 0; i < size; i++) {
            rInt.read();
        }
    }

    private static void writeDate(Organization.Position position, DataOutputStream dos) throws IOException {
        dos.writeInt(position.getStartDate().getYear());
        dos.writeInt(position.getStartDate().getMonthValue());
        dos.writeInt(position.getEndDate().getYear());
        dos.writeInt(position.getEndDate().getMonthValue());
    }

    private static LocalDate readDate(DataInputStream dis) throws IOException {
        return DateUtil.of(dis.readInt(), dis.readInt());
    }
}
