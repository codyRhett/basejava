package ru.javawebinar.storage.strategy;

import ru.javawebinar.model.AbstractSection;
import ru.javawebinar.model.ContactsType;
import ru.javawebinar.model.Resume;
import ru.javawebinar.model.SectionType;

import java.io.*;
import java.util.Map;

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

            dos.writeInt(resume.getSections().size());

            for(Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                dos.writeUTF(entry.getKey().getTitle());
                dos.writeUTF(entry.getValue().toString());
            }
            // TO DO Implements section
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
                resume.addSection(SectionType.valueOf(dis.readUTF()), dis.readUTF());
            }

            return resume;
        }
    }
}
