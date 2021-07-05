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
    protected static final File STORAGE_DIR = new File("C:\\Qt_prj\\jav\\basejava\\src\\ru\\javawebinar\\storage\\storageFiles");
    //protected static final File STORAGE_DIR = new File("/home/artem/java/basejava/basejava/src/ru/javawebinar/storage/storageFiles");

    protected Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID = "uuid";

    private static final Resume R1;
    private static final Resume R2;
    private static final Resume R3;
    private static final Resume R;

    static {
        R1 = ResumeTestData.createResume(UUID_1, "sergey");
        R2 = ResumeTestData.createResume(UUID_2, "tanya");
        R3 = ResumeTestData.createResume(UUID_3, "jack");
        R = ResumeTestData.createResume(UUID, "Name");
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