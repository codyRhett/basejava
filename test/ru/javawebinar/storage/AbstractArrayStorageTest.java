package ru.javawebinar.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveStorageException() {
        storage.clear();
        try {
            for (int i = 0; i < 10_000; i++) {
                storage.save(new Resume("UUID" + i));
            }
        } catch(StorageException e) {
            Assert.fail("Массив переполнен раньше времени!");
        }
        storage.save(new Resume(UUID + 10000));
    }
}