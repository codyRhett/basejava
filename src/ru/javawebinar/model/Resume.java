package ru.javawebinar.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume{
    // Unique identifier
    private String uuid;
    private String fullName;
    private Map<ContactsType, String> contactsMap = new EnumMap<>(ContactsType.class);
    private Map<SectionType, AbstractSection> sectionsMap = new EnumMap<>(SectionType.class);

    public Resume(String uuid) {
        this.uuid = uuid;
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
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

        boolean boo = sectionsMap.values().equals(resume.sectionsMap.values());
        boolean boo1 = contactsMap.equals(resume.contactsMap);
        boolean boo2 = sectionsMap.equals(sectionsMap);

        return uuid.equals(resume.uuid) & fullName.equals(resume.fullName) & contactsMap.equals(resume.contactsMap) & sectionsMap.equals(resume.sectionsMap);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode() + fullName.hashCode() + contactsMap.hashCode() + sectionsMap.hashCode();
    }

    @Override
    public String toString() {
        return uuid;
    }
}
