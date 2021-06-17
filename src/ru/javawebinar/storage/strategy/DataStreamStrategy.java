package ru.javawebinar.storage.strategy;

import ru.javawebinar.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataStreamStrategy implements Strategy {
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


            // Запись секций TextSection в поток
            dos.writeInt((int) resume.getSectionsAll()
                    .entrySet().stream()
                    .filter(x -> x.getValue().getClass() == TextSection.class).count());

            resume.getSectionsAll().forEach((key, value) -> {
                if (value.getClass() == TextSection.class) {
                    try {
                        dos.writeUTF(key.name());
                        dos.writeUTF(value.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            // Запись секций OrganizationSection в поток
            // Записываем количество секций типа OrganizationSection в поток
            dos.writeInt((int) resume.getSectionsAll()
                    .entrySet().stream()
                    .filter(x -> x.getValue().getClass() == OrganizationSection.class).count());


            for (Map.Entry<SectionType, AbstractSection> x : resume.getSectionsAll().entrySet()) {
                if (x.getValue().getClass() == OrganizationSection.class) {
                    try {
                        // Имя секции
                        dos.writeUTF(x.getKey().name());

                        // Получаем секцию с организацией/учебным заведением
                        OrganizationSection orgSec = (OrganizationSection)(resume
                                .getSection(SectionType.valueOf(x.getKey().toString())));

                        // Считываем количество организаций
                        dos.writeInt(orgSec.getOrganizations().size());

                        // пробегаемся по организициям
                        for (Organization orgs : orgSec.getOrganizations()) {
                            dos.writeUTF(orgs.getHomePage().getName());
                            dos.writeUTF(orgs.getHomePage().getUrl());

                            // Считываем количество позиций для одной организации
                            dos.writeInt(orgs.getPositions().size());

                            // Пробегаемся по позициям в пределах одной организации
                            for (Organization.Position pos : orgs.getPositions()) {
                                dos.writeUTF(pos.getStartDate().toString());
                                dos.writeUTF(pos.getEndDate().toString());
                                dos.writeUTF(pos.getTitle());
                                dos.writeUTF(pos.getDescription());
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
          }

//            dos.writeInt(resume.getSectionsAll()
//                    .entrySet().stream()
//                    .filter(x -> x.getValue().getClass() == ListSection.class)
//                    .collect(Collectors.toList()).size());
//
//            for (Map.Entry<SectionType, AbstractSection> x : resume.getSectionsAll().entrySet()) {
//                if (x.getValue().getClass() == ListSection.class) {
//                    try {
//                        dos.writeUTF(x.getKey().name());
//                        dos.writeUTF(x.getValue().toString());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
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


            return resume;
        }
    }
}
