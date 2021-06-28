package ru.javawebinar.storage.strategy;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.*;
import ru.javawebinar.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamStrategy implements Strategy {
    public <K,V> void writeWithException(Map<K, V> collection,
                                         DataOutputStream dos,
                                         WriteInterface<K, V> wInt) throws IOException{
        for(Map.Entry<K, V> entry : collection.entrySet()) {
            wInt.doWriteData(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream file) throws IOException {
        try(DataOutputStream dos = new DataOutputStream(file)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactsType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            writeWithException(contacts, dos, new WriteInterface<ContactsType, String>() {
                @Override
                public void doWriteData(ContactsType contactsType, String s) throws IOException {
                    try {
                        dos.writeUTF(contactsType.name());
                        dos.writeUTF(s);
                    } catch (IOException e) {
                        throw new IOException(e);
                    }
                }
            });

//            for(Map.Entry<ContactsType, String> entry : resume.getContacts().entrySet()) {
//                dos.writeUTF(entry.getKey().name());
//                dos.writeUTF(entry.getValue());
//            }

            Map<SectionType, AbstractSection> sections = resume.getSectionsAll();
            dos.writeInt(sections.size());
            writeWithException(sections, dos, (sectionsType, s) -> {
                try {
                    dos.writeUTF(sectionsType.name());

                    switch (sectionsType) {
                        case PERSONAL:
                        case POSITION:
                            dos.writeUTF(s.toString());
                            break;

                        case ACHIEVEMENT:
                        case QUALIFICATION:
                            List<String> lString = ((ListSection)resume
                                    .getSection(sectionsType))
                                    .getItems();

                            dos.writeInt(lString.size());

                            lString.forEach(listItem -> {
                                try {
                                    dos.writeUTF(listItem);
                                } catch (IOException e) {
                                    throw new StorageException("Write error", e);
                                }
                            });
                            break;

                        case EXPERIENCE:
                        case EDUCATION:
                            List<Organization> organizations = ((OrganizationSection)(resume
                                    .getSection(sectionsType)))
                                    .getOrganizations();

                            // Write number of organizations to dataStream
                            dos.writeInt(organizations.size());
                            // Go over organizations within one section
                            for (Organization org : organizations) {
                                Link homePage = org.getHomePage();
                                dos.writeUTF(homePage.getName());
                                dos.writeUTF(homePage.getUrl());

                                List<Organization.Position> orgPos = org.getPositions();
                                // Write position's number for one organization
                                dos.writeInt(orgPos.size());

                                // Go over positions within one organization
                                for (Organization.Position pos : orgPos) {
                                    writeDate(pos, dos);
                                    dos.writeUTF(pos.getTitle());
                                    dos.writeUTF(pos.getDescription());
                                }
                            }
                            break;
                    }
                } catch (IOException e) {
                    throw new IOException(e);
                }
            });

//            dos.writeInt(resume.getSectionsAll().size());
//            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSectionsAll().entrySet()) {
//                SectionType key = entry.getKey();
//                dos.writeUTF(key.name());
//                switch (entry.getKey()) {
//                    case PERSONAL:
//                    case POSITION:
//                        dos.writeUTF(entry.getValue().toString());
//                        break;
//
//                    case ACHIEVEMENT:
//                    case QUALIFICATION:
//                        List<String> lString = ((ListSection)resume
//                                .getSection(key))
//                                .getItems();
//
//                        dos.writeInt(lString.size());
//                        lString.forEach(listItem -> {
//                            try {
//                                dos.writeUTF(listItem);
//                            } catch (IOException e) {
//                                throw new StorageException("Write error", e);
//                            }
//                        });
//                        break;
//
//                    case EXPERIENCE:
//                    case EDUCATION:
//                        List<Organization> organizations = ((OrganizationSection)(resume
//                                .getSection(key)))
//                                .getOrganizations();
//
//                        // Write number of organizations to dataStream
//                        dos.writeInt(organizations.size());
//                        // Go over organizations within one section
//                        for (Organization org : organizations) {
//                            Link homePage = org.getHomePage();
//                            dos.writeUTF(homePage.getName());
//                            dos.writeUTF(homePage.getUrl());
//
//                            List<Organization.Position> orgPos = org.getPositions();
//                            // Write position's number for one organization
//                            dos.writeInt(orgPos.size());
//
//                            // Go over positions within one organization
//                            for (Organization.Position pos : orgPos) {
//                                writeDate(pos, dos);
//                                dos.writeUTF(pos.getTitle());
//                                dos.writeUTF(pos.getDescription());
//                            }
//                        }
//                        break;
//
//                    default:
//                        break;
//                }
//            }
        }
    }

    @Override
    public Resume doRead(InputStream file) throws IOException {
        try(DataInputStream dis = new DataInputStream(file)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
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
                        String value = dis.readUTF();
                        resume.addSection(secKey, new TextSection(value));
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

                            // Read number of positions within one organization
                            int sizePos = dis.readInt();
                            // Create List with positions
                            List<Organization.Position> orgPos = new ArrayList<>();
                            for(int k = 0; k < sizePos; k++) {
                                LocalDate startDate = readDate(dis);
                                LocalDate endDate = readDate(dis);

                                String title = dis.readUTF();
                                String description = dis.readUTF();
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
