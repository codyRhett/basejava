package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

public interface Storage {
    void clear();

    void update(Resume resume);

    Resume get(Resume resume);

    void delete(Resume resume);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll();

    int size();
}
