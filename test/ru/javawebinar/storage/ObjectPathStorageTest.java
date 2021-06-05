package ru.javawebinar.storage;

import ru.javawebinar.strategy.ObjectStreamStrategy;

public class ObjectPathStorageTest extends AbstractStorageTest {
    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_PATH, new ObjectStreamStrategy()));
    }
}