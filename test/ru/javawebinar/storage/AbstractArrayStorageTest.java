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
    public void saveOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("UUID" + i));
            }
        } catch(StorageException e) {
            Assert.fail("Массив переполнен раньше времени!");
        }
        storage.save(new Resume(uuid_4.toString() + AbstractArrayStorage.STORAGE_LIMIT));
    }
}