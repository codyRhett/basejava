package ru.javawebinar.storage.strategy;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamStrategy implements Strategy {
    private int getSize(Resume r, AbstractSection t) {
        return  ((int) r.getSectionsAll()
                .entrySet().stream()
                .filter(x -> x.getValue().getClass().equals(t.getClass())).count());
    }

    @Override
    public void doWrite(Resume resume, OutputStream file) throws IOException {
        try(DataOutputStream dos = new DataOutputStream(file)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            dos.writeInt(resume.getContacts().size());
            for(Map.Entry<ContactsType, String> entry : resume.getContacts().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            dos.writeInt(getSize(resume, new TextSection()));
            dos.writeInt(getSize(resume, new OrganizationSection()));
            dos.writeInt(getSize(resume, new ListSection()));

            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSectionsAll().entrySet()) {
                switch (entry.getKey().name()) {
                    case "PERSONAL":
                    case "POSITION":
                        dos.writeUTF(entry.getKey().name());
                        dos.writeUTF(entry.getValue().toString());
                        break;

                    case "ACHIEVEMENT":
                    case "QUALIFICATION":
                        dos.writeUTF(entry.getKey().name());
                        List<String> lString = ((ListSection)resume
                                .getSection(SectionType.valueOf(entry.getKey().toString())))
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

                    case "EXPERIENCE":
                    case "EDUCATION":
                        // Имя секции
                        dos.writeUTF(entry.getKey().name());

                        List<Organization> organizations = ((OrganizationSection)(resume
                                .getSection(SectionType.valueOf(entry.getKey().toString()))))
                                .getOrganizations();

                        // Считываем количество организаций
                        dos.writeInt(organizations.size());
                        // пробегаемся по организициям
                        for (Organization orgs : organizations) {
                            dos.writeUTF(orgs.getHomePage().getName());
                            dos.writeUTF(orgs.getHomePage().getUrl());

                            List<Organization.Position> orgsPos = orgs.getPositions();
                            // Считываем количество позиций для одной организации
                            dos.writeInt(orgsPos.size());

                            // Пробегаемся по позициям в пределах одной организации
                            for (Organization.Position pos : orgsPos) {
                                dos.writeUTF(pos.getStartDate().toString());
                                dos.writeUTF(pos.getEndDate().toString());
                                dos.writeUTF(pos.getTitle());
                                dos.writeUTF(pos.getDescription());
                            }
                        }
                        break;

                    default:
                        break;

                }
//                if (entry.getKey().name().equals("TextSection")) {
//                    dos.writeUTF(entry.getKey().name());
//                    dos.writeUTF(entry.getValue().toString());
//                }
//
//                if (entry.getKey().name().equals("OrganizationSection")) {
//                    // Имя секции
//                    dos.writeUTF(entry.getKey().name());
//
//                    List<Organization> organizations = ((OrganizationSection)(resume
//                            .getSection(SectionType.valueOf(entry.getKey().toString()))))
//                            .getOrganizations();
//
//                    // Считываем количество организаций
//                    dos.writeInt(organizations.size());
//                    // пробегаемся по организициям
//                    for (Organization orgs : organizations) {
//                        dos.writeUTF(orgs.getHomePage().getName());
//                        dos.writeUTF(orgs.getHomePage().getUrl());
//
//                        List<Organization.Position> orgsPos = orgs.getPositions();
//                        // Считываем количество позиций для одной организации
//                        dos.writeInt(orgsPos.size());
//
//                        // Пробегаемся по позициям в пределах одной организации
//                        for (Organization.Position pos : orgsPos) {
//                            dos.writeUTF(pos.getStartDate().toString());
//                            dos.writeUTF(pos.getEndDate().toString());
//                            dos.writeUTF(pos.getTitle());
//                            dos.writeUTF(pos.getDescription());
//                        }
//                    }
//                }
//
//                if (entry.getKey().name().equals("ListSection")) {
//                    dos.writeUTF(entry.getKey().name());
//                    List<String> lString = ((ListSection)resume
//                            .getSection(SectionType.valueOf(entry.getKey().toString())))
//                            .getItems();
//
//                    dos.writeInt(lString.size());
//                    lString.forEach(listItem -> {
//                        try {
//                            dos.writeUTF(listItem);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    });
//                }
            }
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

            int sizeSec = dis.readInt();
            for(int i = 0; i < sizeSec; i++) {
                resume.addSection(SectionType.valueOf(dis.readUTF()), new TextSection(dis.readUTF()));
            }

            int sizeOrgSec = dis.readInt();
            // Пробегаемся по секциям типа OrganizationSection
            //Map<SectionType, OrganizationSection> orgSec = null;
            for(int i = 0; i < sizeOrgSec; i++) {
                // Считываем имя секции
                SectionType secType = SectionType.valueOf(dis.readUTF());
                List<Organization> orgs = new ArrayList<>();

                // Считываем число организаций в секции
                int sizeOrgs = dis.readInt();
                // Пробегаемся по организациям
                for(int j = 0; j < sizeOrgs; j++) {
                    String name = dis.readUTF();
                    String url = dis.readUTF();

                    // Считываем число позиций в организации
                    int sizePos = dis.readInt();
                    // Создаем List с позициями
                    List<Organization.Position> orgPos = new ArrayList<>();
                    for(int k = 0; k < sizePos; k++) {
                        LocalDate startDate = LocalDate.parse(dis.readUTF());
                        LocalDate endDate = LocalDate.parse(dis.readUTF());
                        String title = dis.readUTF();
                        String description = dis.readUTF();
                        orgPos.add(new Organization.Position(title, description, startDate, endDate));
                    }
                    orgs.add(new Organization(name, url, orgPos));
                }
                resume.addSection(secType, new OrganizationSection(orgs));
            }

            int sizeList = dis.readInt();
            for(int i = 0; i < sizeList; i++) {
                SectionType secType = SectionType.valueOf(dis.readUTF());

                int sizeItems = dis.readInt();
                List<String> lString = new ArrayList<>();
                for (int j = 0; j < sizeItems; j++) {
                    lString.add(dis.readUTF());
                }
                resume.addSection(secType, new ListSection(lString));
            }

            return resume;
        }
    }
}