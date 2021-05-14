package ru.javawebinar.model;

import java.util.EnumMap;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume{
    // Unique identifier
    private String uuid;

    private String fullName;

    EnumMap<ContactsType, String> contactsMap = new EnumMap<>(ContactsType.class);
    EnumMap<SectionType, AbstractSection> sectionsMap = new EnumMap<>(SectionType.class);

    public Resume() {
        this(UUID.randomUUID().toString());
    }
    public Resume(String uuid) {
        this.uuid = uuid;
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public AbstractSection getSection(SectionType secType) {
        return sectionsMap.get(secType);
    }

    public void addSection(SectionType secType, AbstractSection section) {
        sectionsMap.put(secType, section);
    }

    public void addContact(ContactsType contactType, String contact) {
        contactsMap.put(contactType, contact);
    }


    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return uuid;
    }
}
