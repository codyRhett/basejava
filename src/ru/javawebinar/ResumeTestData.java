package ru.javawebinar;

import ru.javawebinar.model.*;
import ru.javawebinar.storage.MapStorage;
import java.time.LocalDate;
import java.time.Month;

public class ResumeTestData {
    protected static MapStorage storage = new MapStorage();
    static TextSection position = new TextSection("Position1");
    static TextSection personal = new TextSection("My personal");
    static ListSection achievements = new ListSection("Ach_1", "Ach_2", "Ach_3");
    static ListSection qualifications = new ListSection("Q_1", "Q_2", "Q_3");
    static OrganizationSection experience = new OrganizationSection( new Organization("Apple", "https://www.apple.com/ru/",
            new Organization.Position(
                    2005, Month.JANUARY, 2006, Month.JANUARY, "title1", null),
            new Organization.Position(
                    2005, Month.JANUARY, 2006, Month.JANUARY, "title2", "description2")) );

    static OrganizationSection education = new OrganizationSection( new Organization("MGU", null,
            new Organization.Position(
                    2001, Month.JANUARY, 2010, Month.JANUARY, "title1111", null),
            new Organization.Position(
                    2005, Month.JANUARY, 2020, Month.JANUARY, "title222", "description2222")) );


    public static Resume createResume (String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
//        resume.addSection(SectionType.EXPERIENCE, experience);
//        resume.addSection(SectionType.EDUCATION, education);
        resume.addSection(SectionType.POSITION, position);
        resume.addSection(SectionType.PERSONAL, personal);
        resume.addSection(SectionType.ACHIEVEMENT, achievements);
        resume.addSection(SectionType.QUALIFICATION, qualifications);
        resume.addContact(ContactsType.MOBILE, "89261234567");
        resume.addContact(ContactsType.SKYPE, "Skype");
        resume.addContact(ContactsType.MAIL, "123@yandex.ru");
        resume.addContact(ContactsType.GITHUB, "github_account");

        return resume;
    }

    public static void main(String[] args) {
        Resume r = createResume("uuid1", "sergey");
        storage.save(r);

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
            System.out.println(r.getSection(type).toString());
            System.out.println();
        }

        for (ContactsType type : ContactsType.values()) {
            System.out.println(type.getTitle());
            System.out.println(r.getContact(type));
            System.out.println();
        }
    }
}
