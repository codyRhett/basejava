package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected abstract int checkResume(Resume resume);
}
