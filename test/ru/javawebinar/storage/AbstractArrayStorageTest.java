package ru.javawebinar.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;

public abstract class AbstractArrayStorageTest {
    protected Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_3));
        storage.save(new Resume(UUID_2));
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
    public void updateNotExistResume() {
        storage.update(new Resume("uuid7"));
    }

    @Test
    public void update() {
        storage.update(storage.get(UUID_1));
        Assert.assertEquals(new Resume(UUID_1), storage.get(UUID_1));
        storage.update(storage.get(UUID_2));
        Assert.assertEquals(new Resume(UUID_2), storage.get(UUID_2));
        storage.update(storage.get(UUID_3));
        Assert.assertEquals(new Resume(UUID_3), storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(new Resume(UUID_1));
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistException() {
        storage.delete(new Resume("uuid5"));
    }

    @Test(expected = StorageException.class)
    public void saveStorageException() {
        storage.clear();
        for (int i = 0; i <= 10_000; i++) {
            storage.save(new Resume("uuid" + i));
        }
        Assert.fail("Массив переполнен!!!!!");
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistException() {
        storage.get("dummy");
    }

    @Test
    public void getExist() {
        Assert.assertEquals(new Resume(UUID_1), storage.get(UUID_1));
        Assert.assertEquals(new Resume(UUID_2), storage.get(UUID_2));
        Assert.assertEquals(new Resume(UUID_3), storage.get(UUID_3));
    }

    @Test
    public void getAll() {
        saveOrderTest();
    }

    @Test
    public abstract void saveOrderTest();
}