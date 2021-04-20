package ru.javawebinar.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;

import java.util.Arrays;
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
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clearSize() {
        storage.delete(new Resume(UUID_1, NAME_1));
        Assert.assertEquals(2, storage.size());
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
        storage.update(new Resume(UUID, NAME));
    }

    @Test
    public void update() {
        storage.update(storage.get(UUID_1));
        Assert.assertEquals(new Resume(UUID_1, NAME_1), storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(new Resume(UUID_1, NAME_1));
        storage.get(UUID_1);
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistException() {
        storage.delete(new Resume(UUID));
    }

    @Test
    public void save() {
        Resume r = new Resume(UUID_1, NAME_1);
        Assert.assertEquals(r, storage.get(UUID_1));
        Assert.assertEquals(3, storage.size());
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
    public void getAll() {
        Resume[] r = storage.getAll();
        Assert.assertTrue(Arrays.asList(r).contains(new Resume(UUID_1, NAME_1)));
        Assert.assertTrue(Arrays.asList(r).contains(new Resume(UUID_2, NAME_2)));
        Assert.assertTrue(Arrays.asList(r).contains(new Resume(UUID_3, NAME_3)));
    }

    @Test
    public void getAllSorted() {
        List<Resume> listStorage = null;
        listStorage = storage.getAllSorted();

        Assert.assertEquals(listStorage.get(0).getFullName(), NAME_3);
        Assert.assertEquals(listStorage.get(1).getFullName(), NAME_1);
        Assert.assertEquals(listStorage.get(2).getFullName(), NAME_2);
    }

    @Test
    public void getAllSortedNameEqual() {
        storage.clear();
        storage.save(new Resume(UUID_1, NAME_1));
        storage.save(new Resume(UUID_2, NAME_2));
        storage.save(new Resume(UUID_3, NAME_2));

        List<Resume> listStorage = null;
        listStorage = storage.getAllSorted();

        Assert.assertEquals(listStorage.get(0).getUuid(), UUID_1);
        Assert.assertEquals(listStorage.get(1).getUuid(), UUID_2);
        Assert.assertEquals(listStorage.get(2).getUuid(), UUID_3);
    }
}