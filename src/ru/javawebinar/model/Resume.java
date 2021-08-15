package ru.javawebinar.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Serializable {
    private static final long serialVersionUID = 1L;

    // Unique identifier
    private String uuid;
    private String fullName;
    private final Map<ContactsType, String> contactsMap = new EnumMap<>(ContactsType.class);
    private final Map<SectionType, AbstractSection> sectionsMap = new EnumMap<>(SectionType.class);

    public Resume() {
    }

    public Resume(String uuid) {
        this.uuid = uuid;
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public void addSection(SectionType secType, AbstractSection section) {
        sectionsMap.put(secType, section);
    }

    public AbstractSection getSection(SectionType secType) {
        return sectionsMap.get(secType);
    }

    public Map<SectionType, AbstractSection> getSectionsAll() {
        return sectionsMap;
    }

    public String getContact(ContactsType secType) {
        return contactsMap.get(secType);
    }

    public Map<ContactsType, String> getContacts() {
        return contactsMap;
    }

    public void addContact(ContactsType contactType, String contact) {
        contactsMap.put(contactType, contact);
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

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
