package ru.javawebinar;

import ru.javawebinar.model.*;
import ru.javawebinar.storage.MapStorage;

import java.time.LocalDate;

public class ResumeTestData {
    protected static MapStorage storage = new MapStorage();

    protected static TextSection position = new TextSection(SectionType.POSITION.getTitle());
    protected static TextSection personal = new TextSection(SectionType.PERSONAL.getTitle());
    protected static ListSection achievements = new ListSection(SectionType.ACHIEVEMENT.getTitle());
    protected static ListSection qualifications = new ListSection(SectionType.QUALIFICATION.getTitle());
    protected static ExperienceSection experience = new ExperienceSection(SectionType.EXPERIENCE.getTitle());
    protected static ExperienceSection education = new ExperienceSection(SectionType.EDUCATION.getTitle());

    public static Resume createResume (String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        //position = new TextSection(SectionType.POSITION.getTitle());
        position.setText("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");

        //personal = new TextSection(SectionType.PERSONAL.getTitle());
        personal.setText("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        //achievements = new ListSection(SectionType.ACHIEVEMENT.getTitle());
        achievements.addTextToList("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)" +
                "\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievements.addTextToList("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievements.addTextToList("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, " +
                "Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");

        //qualifications = new ListSection(SectionType.QUALIFICATION.getTitle());
        qualifications.addTextToList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.addTextToList("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.addTextToList("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");

        //experience = new ExperienceSection(SectionType.EXPERIENCE.getTitle());
        Organization orgJavaOnline = new Organization("Java Online Projects");
        orgJavaOnline.setUrl("https://career.luxoft.com/locations/russia/");

        Experience experienceJavaOrg = new Experience();
        experienceJavaOrg.setPosition("Автор проекта");
        experienceJavaOrg.setDateStart(LocalDate.of(1914, 12, 31));
        experienceJavaOrg.setDateEnd(LocalDate.of(1920, 12, 31));
        experienceJavaOrg.setResponsibilities("Создание, организация и проведение Java онлайн проектов и стажировок.");
        orgJavaOnline.addExperienceToList(experienceJavaOrg);
        experience.addOrganizationToList(orgJavaOnline);

        Organization orgWrike = new Organization("Wrike");
        orgWrike.setUrl("https://www.wrike.com/vj/");
        Experience experienceWrikeOrg = new Experience();
        experienceWrikeOrg.setPosition("Старший разработчик (backend)");
        experienceWrikeOrg.setResponsibilities("Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, " +
                "Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        experienceWrikeOrg.setDateStart(LocalDate.of(1945, 12, 31));
        experienceWrikeOrg.setDateEnd(LocalDate.of(1946, 12, 31));
        orgWrike.addExperienceToList(experienceWrikeOrg);

        Experience experienceWrikeOrg1 = new Experience();
        experienceWrikeOrg1.setPosition("Инженер");
        experienceWrikeOrg1.setResponsibilities("Устранение неполадок");
        experienceWrikeOrg1.setDateStart(LocalDate.of(1976, 12, 31));
        experienceWrikeOrg1.setDateEnd(LocalDate.of(1986, 12, 31));
        orgWrike.addExperienceToList(experienceWrikeOrg1);

        experience.addOrganizationToList(orgWrike);

        //education = new ExperienceSection(SectionType.EDUCATION.getTitle());
        Organization CourseraOrg = new Organization("Coursera");
        CourseraOrg.setUrl("https://www.coursera.org/learn/progfun1");
        Experience educationCoursera = new Experience();
        educationCoursera.setPosition("Магистр");
        educationCoursera.setResponsibilities("\"Functional Programming Principles in Scala\" by Martin Odersky");
        educationCoursera.setDateStart(LocalDate.of(1946, 12, 31));
        educationCoursera.setDateEnd(LocalDate.of(1949, 12, 31));
        CourseraOrg.addExperienceToList(educationCoursera);

        education.addOrganizationToList(CourseraOrg);

        Organization LuxoftOrg = new Organization("Luxoft");
        Experience educationLuxoft = new Experience();
        educationLuxoft.setPosition("Магистр");
        educationLuxoft.setResponsibilities("Курс Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.");
        educationLuxoft.setDateStart(LocalDate.of(1987, 12, 31));
        educationLuxoft.setDateEnd(LocalDate.of(1989, 12, 31));

        LuxoftOrg.addExperienceToList(educationLuxoft);

        Experience educationLuxoft1 = new Experience();
        educationLuxoft1.setPosition("Аспирант");
        educationLuxoft1.setResponsibilities("Курс программирования");
        educationLuxoft1.setDateStart(LocalDate.of(1990, 12, 31));
        educationLuxoft1.setDateEnd(LocalDate.of(1993, 12, 31));
        LuxoftOrg.addExperienceToList(educationLuxoft1);

        education.addOrganizationToList(LuxoftOrg);

        resume.addSection(SectionType.POSITION, position);
        resume.addSection(SectionType.PERSONAL, personal);
        resume.addSection(SectionType.ACHIEVEMENT, achievements);
        resume.addSection(SectionType.QUALIFICATION, qualifications);
        resume.addSection(SectionType.EXPERIENCE, experience);
        resume.addSection(SectionType.EDUCATION, education);

        resume.addContact(ContactsType.MOBILENUMBER, "+7(921) 855-0482");
        resume.addContact(ContactsType.SKYPE, "grigory.kislin");
        resume.addContact(ContactsType.EMAIL, "gkislin@yandex.ru");

        return resume;
    }

    public static void main(String[] args) {
        Resume r = createResume("uuid1", "sergey");
        storage.save(r);

        for (SectionType type : SectionType.values()) {
            System.out.println(r.getSection(type).getTitle());
            System.out.println(r.getSection(type).toString());
            System.out.println();
        }
    }
}
