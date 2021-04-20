package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.List;

public interface Storage {
    void clear();

    void update(Resume resume);

    Resume get(String uuid);

    void delete(Resume resume);

    void save(Resume resume);
    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll();
    // return list sorted by name
    List<Resume> getAllSorted();

    int size();
}
