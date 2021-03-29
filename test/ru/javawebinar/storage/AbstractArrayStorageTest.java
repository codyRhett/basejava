package ru.javawebinar.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.testng.annotations.Parameters;
import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
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
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void clearNotExistStorage() {
        storage.clear();
        storage.get(UUID_1);
        storage.get(UUID_2);
        storage.get(UUID_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistResume() {
        Resume r = new Resume("uuid7");
        storage.update(r);
    }

    @Test
    public void update() {
        storage.update(storage.get(UUID_1));
        storage.update(storage.get(UUID_2));
        storage.update(storage.get(UUID_3));
    }

    @Test
    public void delete() {
        Resume r = new Resume(UUID_1);
        storage.delete(r);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistException() {
        Resume r = new Resume("uuid5");
        storage.delete(r);
    }

    @Test
    public void saveOrderTest() {
        storage.clear();
        storage.update(storage.get(UUID_1));
        storage.update(storage.get(UUID_2));
        storage.update(storage.get(UUID_3));
    }

    @Test(expected = StorageException.class)
    public void saveStorageException() {
        storage.clear();
        for (int i = 0; i <= 10_000; i++) {
            storage.save(new Resume(new StringBuilder().append("uuid").append(i).toString()));
        }
        Assert.fail("Массив переполнен!!!!!");
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistException() {
        storage.get("dummy");
    }

    @Test
    public void get() {
        storage.get(UUID_1);
        storage.get(UUID_2);
        storage.get(UUID_3);
    }

    @Test
    public void getAll() {
    }
}