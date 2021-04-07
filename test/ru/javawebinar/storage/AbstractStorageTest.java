package ru.javawebinar.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID = "uuid";

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clearSize() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertArrayEquals(new Resume[0], storage.getAll());
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistException() {
        storage.update(new Resume("uuid7"));
    }

    @Test
    public void update() {
        storage.update(storage.get(UUID_1));
        Assert.assertEquals(new Resume(UUID_1), storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(new Resume(UUID_1));
        storage.get(UUID_1);
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistException() {
        storage.delete(new Resume(UUID));
    }

    @Test
    public void save() {
        Resume r = new Resume(UUID_1);
        Assert.assertEquals(r, storage.get(UUID_1));
        Assert.assertEquals(3, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistException() {
        storage.save(new Resume(UUID_1));
    }

    @Test(expected = StorageException.class)
    public void saveStorageException() {
        storage.clear();
        try {
            for (int i = 0; i < 10_000; i++) {
                storage.save(new Resume("UUID" + i));
            }
        } catch(StorageException e) {
            Assert.fail("Массив переполнен!");
        }
        storage.save(new Resume(UUID + 10000));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistException() {
        storage.get(UUID);
    }

    @Test
    public void getExistException() {

        Assert.assertEquals(new Resume(UUID_1), storage.get(UUID_1));
    }

    @Test
    public void getAll() {
        Resume[] r = storage.getAll();
        Assert.assertEquals(new Resume(UUID_1), r[0]);
        Assert.assertEquals(new Resume(UUID_2), r[1]);
        Assert.assertEquals(new Resume(UUID_3), r[2]);
    }
}