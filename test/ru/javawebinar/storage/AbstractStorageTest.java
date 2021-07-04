package ru.javawebinar.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.ResumeTestData;
import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.*;

import java.io.File;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStorageTest {
    //protected static final File STORAGE_DIR = new File("C:\\Qt_prj\\jav\\basejava\\src\\ru\\javawebinar\\storage\\storageFiles");
    protected static final File STORAGE_DIR = new File("/home/artem/java/basejava/basejava/src/ru/javawebinar/storage/storageFiles");

    protected Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID = "uuid";

    private static Resume R1;
    private static Resume R2;
    private static Resume R3;
    private static Resume R;

    static {
        R1 = new Resume(UUID_1, "sergey");
        R2 = new Resume(UUID_2, "tanya");
        R3 = new Resume(UUID_3, "jack");
        R = new Resume(UUID, "Name");

        //protected static MapStorage storage = new MapStorage();
        TextSection position = new TextSection("Position1");
        TextSection personal = new TextSection("My personal");
        ListSection achievements = new ListSection("Ach_1", "Ach_2");
        ListSection qualifications = new ListSection("Q_1", "Q_2", "Q_3");
        OrganizationSection experience = new OrganizationSection( new Organization("Apple", "https://www.apple.com/ru/",
                new Organization.Position(
                        2005, Month.JANUARY, 2006, Month.JANUARY, "title1", null),
                new Organization.Position(
                        2005, Month.JANUARY, 2006, Month.JANUARY, "title2", "description2")) );

        OrganizationSection education = new OrganizationSection( new Organization("MGU", null,
                new Organization.Position(
                        2001, Month.JANUARY, 2010, Month.JANUARY, "title1111", null),
                new Organization.Position(
                        2005, Month.JANUARY, 2020, Month.JANUARY, "title222", "description2222")) );


        R1.addSection(SectionType.EXPERIENCE, experience);
        R1.addSection(SectionType.EDUCATION, education);
        R1.addSection(SectionType.POSITION, position);
        R1.addSection(SectionType.PERSONAL, personal);
        R1.addSection(SectionType.ACHIEVEMENT, achievements);
        R1.addSection(SectionType.QUALIFICATION, qualifications);
        R1.addContact(ContactsType.MOBILE, "89261234567");
        R1.addContact(ContactsType.SKYPE, "Skype");
        R1.addContact(ContactsType.MAIL, "123@yandex.ru");
        R1.addContact(ContactsType.GITHUB, "github_account");

        R2.addSection(SectionType.EXPERIENCE, experience);
        R2.addSection(SectionType.EDUCATION, education);
        R2.addSection(SectionType.POSITION, position);
        R2.addSection(SectionType.PERSONAL, personal);
        R2.addSection(SectionType.ACHIEVEMENT, achievements);
        R2.addSection(SectionType.QUALIFICATION, qualifications);
        R2.addContact(ContactsType.MOBILE, "89261234567");
        R2.addContact(ContactsType.SKYPE, "Skype");
        R2.addContact(ContactsType.MAIL, "123@yandex.ru");
        R2.addContact(ContactsType.GITHUB, "github_account");

        R3.addSection(SectionType.EXPERIENCE, experience);
        R3.addSection(SectionType.EDUCATION, education);
        R3.addSection(SectionType.POSITION, position);
        R3.addSection(SectionType.PERSONAL, personal);

        R.addContact(ContactsType.MOBILE, "89261234567");
        R.addContact(ContactsType.SKYPE, "Skype");
        R.addContact(ContactsType.MAIL, "123@yandex.ru");
        R.addContact(ContactsType.GITHUB, "github_account");
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    public void clear() {
        Assert.assertEquals(3, storage.size());
        storage.clear();
        Assert.assertEquals(0, storage.size());
        Assert.assertEquals(new ArrayList<>(), storage.getAllSorted());
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistException() {
        storage.update(R);
    }

    @Test
    public void update() {
        Resume r = R1;
        storage.update(r);
        Assert.assertEquals(r, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistException() {
        storage.delete(UUID);
    }

    @Test
    public void save() {
        Resume r = R;
        storage.save(r);
        Assert.assertEquals(r, storage.get(UUID));
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistException() {
        storage.save(R1);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistException() {
        storage.get(UUID);
    }

    @Test
    public void get() {
        Assert.assertEquals(R1, storage.get(UUID_1));
    }

    @Test
    public void getAllSorted() {
        List<Resume> r = new ArrayList<>();

        r.add(R3);
        r.add(R1);
        r.add(R2);

        Assert.assertEquals(r, storage.getAllSorted());
    }
}