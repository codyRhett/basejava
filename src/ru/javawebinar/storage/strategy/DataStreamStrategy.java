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

//            Map<ContactsType, String> contacts = resume.getContacts();
//
//            Collection<Map.Entry<ContactsType, String>> collection = contacts.entrySet();
//
//            readWithException(collection, dis, entries -> {
//                String key = dis.readUTF();
//                String value = dis.readUTF();
//            });

            int size  = dis.readInt();
            for(int i = 0; i < size; i++) {
                resume.addContact(ContactsType.valueOf(dis.readUTF()), dis.readUTF());
            }

            // Read sections
            int sizeSections = dis.readInt();
            for(int i = 0; i < sizeSections; i++) {
                SectionType secKey = SectionType.valueOf(dis.readUTF());
                switch (secKey) {
                    case PERSONAL:
                    case POSITION:
                        resume.addSection(secKey, new TextSection(dis.readUTF()));
                        break;

                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        int sizeItems = dis.readInt();
                        List<String> lString = new ArrayList<>();
                        for (int j = 0; j < sizeItems; j++) {
                            lString.add(dis.readUTF());
                        }
                        resume.addSection(secKey, new ListSection(lString));
                        break;

                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> orgs = new ArrayList<>();

                        // Read number of organizations within one sections
                        int sizeOrgs = dis.readInt();
                        // Go over organizations
                        for(int j = 0; j < sizeOrgs; j++) {
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            if (url.equals("")) {
                                url = null;
                            }

                            // Read number of positions within one organization
                            int sizePos = dis.readInt();
                            // Create List with positions
                            List<Organization.Position> orgPos = new ArrayList<>();
                            for(int k = 0; k < sizePos; k++) {
                                LocalDate startDate = readDate(dis);
                                LocalDate endDate = readDate(dis);

                                String title = dis.readUTF();
                                String description = dis.readUTF();
                                if (description.equals("")) {
                                    description = null;
                                }
                                orgPos.add(new Organization.Position(title, description, startDate, endDate));
                            }
                            orgs.add(new Organization(name, url, orgPos));
                        }
                        resume.addSection(secKey, new OrganizationSection(orgs));
                        break;

                    default:
                        break;
                }
            }
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

    private <V> void readWithException( Resume resume,
                                        DataInputStream dis,
                                        ReadInterface<V> rInt) throws IOException{
        int size = dis.readInt();
        for(int i = 0; i < size; i++) {
            rInt.read(resume);
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
