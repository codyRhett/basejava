package ru.javawebinar.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;
import java.util.List;

public abstract class AbstractStorageTest {
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
        storage.save(new Resume(UUID_1, NAME_1));
        storage.save(new Resume(UUID_2, NAME_2));
        storage.save(new Resume(UUID_3, NAME_3));
    }

    @Test
    public void clearSize() {
        Assert.assertEquals(3, storage.size());
        storage.clear();
        Assert.assertEquals(0, storage.size());
        Assert.assertArrayEquals(new Resume[0], storage.getAllSorted().toArray());
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistException() {
        storage.update(new Resume(UUID, NAME));
    }

    @Test
    public void update() {
        Resume r = new Resume(UUID_1, NAME_1);
        storage.update(r);
        Assert.assertEquals(r, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(new Resume(UUID_1, NAME_1));
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistException() {
        storage.delete(new Resume(UUID));
    }

    @Test
    public void save() {
        Resume r = new Resume(UUID, NAME);
        storage.save(r);
        Assert.assertEquals(r, storage.get(UUID));
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistException() {
        storage.save(new Resume(UUID_1, NAME_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistException() {
        storage.get(UUID);
    }

    @Test
    public void get() {
        Assert.assertEquals(new Resume(UUID_1, NAME_1), storage.get(UUID_1));
    }

    @Test
    public void getAllSorted() {
        storage.clear();
        storage.save(new Resume(UUID_1, NAME_1));
        storage.save(new Resume(UUID_2, NAME_2));
        storage.save(new Resume(UUID_3, NAME_2));

        List<Resume> listStorage = storage.getAllSorted();

        Assert.assertEquals(listStorage.get(0), new Resume(UUID_1, NAME_1));
        Assert.assertEquals(listStorage.get(1), new Resume(UUID_2, NAME_2));
        Assert.assertEquals(listStorage.get(2), new Resume(UUID_3, NAME_3));
    }
}