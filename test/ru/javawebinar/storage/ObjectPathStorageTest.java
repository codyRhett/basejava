package ru.javawebinar.storage;

public class ObjectPathStorageTest extends AbstractStorageTest {
    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_PATH, new ObjectStreamStrategy()));
    }
}