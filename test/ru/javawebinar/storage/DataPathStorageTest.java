package ru.javawebinar.storage;

import ru.javawebinar.storage.strategy.DataStreamStrategy;

public class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamStrategy()));
    }
}