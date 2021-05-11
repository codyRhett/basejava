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
        ((SectionText) position).setText("программист JAVA");

        Section personal = new SectionText(SectionType.PERSONAL.getTitle());
        ((SectionText) personal).setText("целеустремленный, обучаемый");

        Section achievements = new SectionList(SectionType.ACHIEVEMENT.getTitle());
        ((SectionList) achievements).addTextToList("Достижение 1");
        ((SectionList) achievements).addTextToList("Достижение 2");
        ((SectionList) achievements).addTextToList("Достижение 3");

        Section qualifications = new SectionList(SectionType.QUALIFICATION.getTitle());
        ((SectionList) qualifications).addTextToList("Квалификация 1");
        ((SectionList) qualifications).addTextToList("Квалификация 2");
        ((SectionList) qualifications).addTextToList("Квалификация 3");

        Section experience = new SectionExperience(SectionType.EXPERIENCE.getTitle());
        Organization siemensOrg = new Organization("Siemens");
        Experience experienceSiemens = new Experience();
//        //experienceSiemens.setDate();
        experienceSiemens.setPosition("Инженер");
        experienceSiemens.setResponsibilities("Разработка систем управления");
        siemensOrg.addExperienceToList(experienceSiemens);

        ((SectionExperience) experience).addOrganizationToList(siemensOrg);

        Resume resume = storage.get(UUID_1);
        resume.addSection(SectionType.POSITION, position);
        resume.addSection(SectionType.PERSONAL, personal);
        resume.addSection(SectionType.ACHIEVEMENT, achievements);
        resume.addSection(SectionType.QUALIFICATION, qualifications);
        resume.addSection(SectionType.EXPERIENCE, experience);

        resume.addContact(ContactsType.MOBILENUMBER, "91234567890");
        resume.addContact(ContactsType.SKYPE, "codyrhett");
        resume.addContact(ContactsType.EMAIL, "codyrhett@yandex.ru");

        storage.save(new Resume(UUID_3, NAME_3));
    }
}
