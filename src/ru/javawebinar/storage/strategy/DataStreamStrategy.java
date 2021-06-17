package ru.javawebinar.storage.strategy;

import org.w3c.dom.Text;
import ru.javawebinar.model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Month;
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
            dos.writeInt(resume.getSectionsAll()
                    .entrySet().stream()
                    .filter(x -> x.getValue().getClass() == TextSection.class)
                    .collect(Collectors.toList()).size());

            for (Map.Entry<SectionType, AbstractSection> x : resume.getSectionsAll().entrySet()) {
                if (x.getValue().getClass() == TextSection.class) {
                    try {
                        dos.writeUTF(x.getKey().name());
                        dos.writeUTF(x.getValue().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            // Запись секций OrganizationSection в поток
            dos.writeInt(resume.getSectionsAll()
                    .entrySet().stream()
                    .filter(x -> x.getValue().getClass() == OrganizationSection.class)
                    .collect(Collectors.toList()).size());

            for (Map.Entry<SectionType, AbstractSection> x : resume.getSectionsAll().entrySet()) {
                if (x.getValue().getClass() == OrganizationSection.class) {
                    try {
                        dos.writeUTF(x.getKey().name());

                        // Получаем секцию с организацией
                        OrganizationSection orgSec = (OrganizationSection)(resume
                                .getSection(SectionType.valueOf(x.getKey().toString())));

                        for (Organization orgs : orgSec.getOrganizations()) {
                            dos.writeUTF(orgs.getHomePage().getName());
                            dos.writeUTF(orgs.getHomePage().getUrl());
                            for (Organization.Position pos : orgs.getPositions()) {
                                dos.writeUTF(pos.getStartDate().toString());
                                dos.writeUTF(pos.getEndDate().toString());
                                dos.writeUTF(pos.getDescription());
                                dos.writeUTF(pos.getTitle());
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

            // TO DO Implements section
        }
    }

//    private void convertData() {
//
//    }

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

            int sizeOrg = dis.readInt();
            for(int i = 0; i < sizeSec; i++) {

                OrganizationSection orgSec = new OrganizationSection(( new Organization(dis.readUTF(), dis.readUTF(),
                        new Organization.Position(
                                dis.readUTF()=\p, Month.JANUARY, 2006, Month.JANUARY, "title1", "description1"),
                        new Organization.Position(
                                2005, Month.JANUARY, 2006, Month.JANUARY, "title1", "description1")) ))
               // resume.addSection(SectionType.valueOf(dis.readUTF()), (OrganizationSection)dis.readUTF());
            }

            return resume;
        }
    }
}
