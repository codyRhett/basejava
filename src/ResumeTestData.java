import ru.javawebinar.model.*;
import ru.javawebinar.storage.MapStorage;

import java.time.LocalDate;

public class ResumeTestData {

    protected static MapStorage storage = new MapStorage();

    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID = "uuid";

    protected static final String NAME_1 = "sergey";
    protected static final String NAME_2 = "tanya";
    protected static final String NAME_3 = "jack";
    protected static final String NAME = "artem";

    public static void main(String[] args) {
        storage.save(new Resume(UUID_1, NAME_1));
        Resume resume = storage.get(UUID_1);

        TextSection position = new TextSection(SectionType.POSITION.getTitle());
        position.setText("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");

        TextSection personal = new TextSection(SectionType.PERSONAL.getTitle());
        personal.setText("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        ListSection achievements = new ListSection(SectionType.ACHIEVEMENT.getTitle());
        achievements.addTextToList("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)" +
                "\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievements.addTextToList("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievements.addTextToList("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, " +
                "Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");

        ListSection qualifications = new ListSection(SectionType.QUALIFICATION.getTitle());
        qualifications.addTextToList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.addTextToList("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.addTextToList("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");

        ExperienceSection experience = new ExperienceSection(SectionType.EXPERIENCE.getTitle());
        Organization orgJavaOnline = new Organization("Java Online Projects");
        Experience experienceJavaOrg = new Experience();
        experienceJavaOrg.setPosition("Автор проекта");
        experienceJavaOrg.setDateStart(LocalDate.of(1914, 12, 31));
        experienceJavaOrg.setDateEnd(LocalDate.of(1920, 12, 31));
        experienceJavaOrg.setResponsibilities("Создание, организация и проведение Java онлайн проектов и стажировок.");
        orgJavaOnline.addExperienceToList(experienceJavaOrg);
        experience.addOrganizationToList(orgJavaOnline);

        Organization orgWrike = new Organization("Wrike");
        Experience experienceWrikeOrg = new Experience();
        experienceWrikeOrg.setPosition("Старший разработчик (backend)");
        experienceWrikeOrg.setResponsibilities("Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, " +
                "Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        experienceWrikeOrg.setDateStart(LocalDate.of(1945, 12, 31));
        experienceWrikeOrg.setDateEnd(LocalDate.of(1946, 12, 31));
        orgWrike.addExperienceToList(experienceWrikeOrg);
        experience.addOrganizationToList(orgWrike);
        
        ExperienceSection education = new ExperienceSection(SectionType.EDUCATION.getTitle());
        Organization CourseraOrg = new Organization("Coursera");
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

        for (SectionType type : SectionType.values()) {
            System.out.println(resume.getSection(type).getTitle());
            System.out.println(resume.getSection(type).toString());
            System.out.println();
        }
    }
}
