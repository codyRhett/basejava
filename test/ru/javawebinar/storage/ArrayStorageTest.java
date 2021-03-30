package ru.javawebinar.storage;

import org.testng.Assert;
import ru.javawebinar.model.Resume;

public class ArrayStorageTest extends AbstractArrayStorageTest {
    public ArrayStorageTest() {
        super(new ArrayStorage());
    }

    public void saveOrderTest() {
        Resume[] r;
        r = storage.getAll();
        Assert.assertEquals(new Resume(UUID_1), r[0]);
        Assert.assertEquals(new Resume(UUID_3), r[1]);
        Assert.assertEquals(new Resume(UUID_2), r[2]);
    }
}