package ru.javawebinar.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.Config;
import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;
import ru.javawebinar.storage.TestData.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.storage.TestData.*;

public abstract class AbstractStorageTest {
    //protected static final File STORAGE_DIR = new File("C:\\Qt_prj\\jav\\basejava\\src\\ru\\javawebinar\\storage\\storageFiles");
    //protected static final File STORAGE_DIR = new File("/home/artem/java/basejava/basejava/src/ru/javawebinar/storage/storageFiles");
    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    protected Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(R3);
        storage.save(R1);

        storage.save(R2);
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
        storage.update(R4);
    }

    @Test
    public void update() {
        Resume r = R1;
        storage.update(r);
        Assert.assertEquals(r, storage.get(uuid_1.toString()));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(uuid_1.toString());
        Assert.assertEquals(2, storage.size());
        storage.get(uuid_1.toString());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistException() {
        storage.delete(uuid_4.toString());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistException() {
        storage.save(R1);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistException() {
        storage.get(uuid_4.toString());
    }

    @Test
    public void get() {
        Assert.assertEquals(R1, storage.get(uuid_1.toString()));
    }

    @Test
    public void getAllSorted() {
        List<Resume> r = new ArrayList<>();

        r.add(R3);
        r.add(R1);
        r.add(R2);

        Assert.assertEquals(r, storage.getAllSorted());
    }

    @Test
    public void save() {
        Resume r = R4;
        storage.save(r);
        Assert.assertEquals(r, storage.get(uuid_4.toString()));
        Assert.assertEquals(4, storage.size());
    }
}