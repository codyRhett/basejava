package ru.javawebinar.storage;

import ru.javawebinar.storage.strategy.ObjectStreamStrategy;

public class ObjectFileStorageTest extends AbstractStorageTest {
    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStrategy()));
    }
}