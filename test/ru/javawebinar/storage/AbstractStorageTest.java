package ru.javawebinar.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.ResumeTestData;
import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("C:\\Qt_prj\\jav\\basejava\\src\\ru\\javawebinar\\storage\\storageFiles");
    protected static final String STORAGE_PATH = "C:\\Qt_prj\\jav\\basejava\\src\\ru\\javawebinar\\storage\\storageFiles";

    protected Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID = "uuid";

    protected static final String NAME_1 = "sergey";
    protected static final String NAME_2 = "tanya";
    protected static final String NAME_3 = "jack";
    protected static final String NAME = "artem";

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(ResumeTestData.createResume(UUID_1, NAME_1));
        storage.save(ResumeTestData.createResume(UUID_2, NAME_2));
        storage.save(ResumeTestData.createResume(UUID_3, NAME_3));
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
        storage.update(new Resume(UUID, NAME));
    }

    @Test
    public void update() {
        Resume r = ResumeTestData.createResume(UUID_1, NAME_1);
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
        Resume r = ResumeTestData.createResume(UUID, NAME);
        storage.save(r);
        Assert.assertEquals(r, storage.get(UUID));
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistException() {
        storage.save(ResumeTestData.createResume(UUID_1, NAME_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistException() {
        storage.get(UUID);
    }

    @Test
    public void get() {
        Assert.assertEquals(ResumeTestData.createResume(UUID_1, NAME_1), storage.get(UUID_1));
    }

    @Test
    public void getAllSorted() {
        List<Resume> r = new ArrayList<>();

        r.add(ResumeTestData.createResume(UUID_3, NAME_3));
        r.add(ResumeTestData.createResume(UUID_1, NAME_1));
        r.add(ResumeTestData.createResume(UUID_2, NAME_2));

        Assert.assertEquals(r, storage.getAllSorted());
    }
}