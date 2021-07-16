package ru.javawebinar.storage;

import ru.javawebinar.Config;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {

        super(new SqlStorage(Config.get().getUrl(), Config.get().getUser(), Config.get().getPassword()));
    }
}