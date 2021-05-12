import ru.javawebinar.model.*;
import ru.javawebinar.storage.MapStorage;
import ru.javawebinar.storage.Storage;

import java.util.EnumSet;

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
        storage.save(new Resume(UUID_2, NAME_2));

        Section position = new SectionText(SectionType.POSITION.getTitle());
        ((SectionText) position).setText("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");

        Section personal = new SectionText(SectionType.PERSONAL.getTitle());
        ((SectionText) personal).setText("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        Section achievements = new SectionList(SectionType.ACHIEVEMENT.getTitle());
        ((SectionList) achievements).addTextToList("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)" +
                "\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        ((SectionList) achievements).addTextToList("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        ((SectionList) achievements).addTextToList("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, " +
                "Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");

        Section qualifications = new SectionList(SectionType.QUALIFICATION.getTitle());
        ((SectionList) qualifications).addTextToList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        ((SectionList) qualifications).addTextToList("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        ((SectionList) qualifications).addTextToList("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");

        Section experience = new SectionExperience(SectionType.EXPERIENCE.getTitle());
        Organization orgJavaOnline = new Organization("Java Online Projects");
        Experience experienceJavaOrg = new Experience();
        experienceJavaOrg.setPosition("Автор проекта");
        experienceJavaOrg.setResponsibilities("Создание, организация и проведение Java онлайн проектов и стажировок.");
        orgJavaOnline.addExperienceToList(experienceJavaOrg);
        ((SectionExperience) experience).addOrganizationToList(orgJavaOnline);

        Organization orgWrike = new Organization("Wrike");
        Experience experienceWrikeOrg = new Experience();
        experienceWrikeOrg.setPosition("Старший разработчик (backend)");
        experienceWrikeOrg.setResponsibilities("\tСтарший разработчик (backend)\n" +
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, " +
                "Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        orgWrike.addExperienceToList(experienceWrikeOrg);
        ((SectionExperience) experience).addOrganizationToList(orgWrike);
        
        Section education = new SectionExperience(SectionType.EDUCATION.getTitle());
        Organization CourseraOrg = new Organization("Coursera");
        Experience educationCoursera = new Experience();
        educationCoursera.setPosition("Магистр");
        educationCoursera.setResponsibilities("\"Functional Programming Principles in Scala\" by Martin Odersky");
        CourseraOrg.addExperienceToList(educationCoursera);

        ((SectionExperience) education).addOrganizationToList(CourseraOrg);

        Organization LuxoftOrg = new Organization("Luxoft");
        Experience educationLuxoft = new Experience();
        educationLuxoft.setPosition("Магистр");
        educationLuxoft.setResponsibilities("Курс Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.");
        LuxoftOrg.addExperienceToList(educationLuxoft);

        ((SectionExperience) education).addOrganizationToList(LuxoftOrg);

        Resume resume = storage.get(UUID_1);
        resume.addSection(SectionType.POSITION, position);
        resume.addSection(SectionType.PERSONAL, personal);
        resume.addSection(SectionType.ACHIEVEMENT, achievements);
        resume.addSection(SectionType.QUALIFICATION, qualifications);
        resume.addSection(SectionType.EXPERIENCE, experience);
        resume.addSection(SectionType.EDUCATION, education);

        resume.addContact(ContactsType.MOBILENUMBER, "91234567890");
        resume.addContact(ContactsType.SKYPE, "codyrhett");
        resume.addContact(ContactsType.EMAIL, "codyrhett@yandex.ru");

        for (SectionType type : SectionType.values()) {
            System.out.prighfntln(type.getTitle());
        }

        storage.save(new Resume(UUID_3, NAME_3));
    }
}
