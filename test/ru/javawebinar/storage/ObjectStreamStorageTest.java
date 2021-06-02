package ru.javawebinar.storage;

public class ObjectStreamStorageTest extends AbstractStorageTest {
    public ObjectStreamStorageTest() {
        super((Storage) new ObjectStreamStorage());
    }
}