package ru.javawebinar.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.testng.annotations.Parameters;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

//@RunWith(value = Parameterized.class)
public abstract class AbstractArrayStorageTest {
    private Storage storage = new ArrayStorage();
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

//    @Parameters
//    public static Collection<Object[]> data() {
//        Object[][] data = new Object[][] { { "uuid1", "uuid2", "uuid3" }, { "uuid1", "uuid2", "uuid3" } };
//        return Arrays.asList(data);
//    }

    @Before
    public void setUp() {
//        storage.clear();
//        storage.save(new Resume(UUID_1));
//        storage.save(new Resume(UUID_2));
//        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistResume() {
        Resume r = new Resume("uuid7");
        storage.update(r);
    }

    @Test
    public void updateExistResume() {
        Resume r = new Resume("uuid1");
        storage.update(r);
    }

    @Test(expected = NotExistStorageException.class)
    public void get() {
        String uuid = "uuid7";
        storage.get(uuid);
    }

    @Test
    public void delete() {
        Resume r = new Resume("uuid1");
        storage.delete(r);
        //Assert.assertArrayEquals();
    }

    @Test
    public void getAll() {
    }

    @Test
    public void save() {
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistException() {
        storage.get("dummy");
    }
}