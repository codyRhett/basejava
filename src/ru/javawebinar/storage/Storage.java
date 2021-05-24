package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.List;

public interface Storage {
    void clear();

    void update(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    void save(Resume resume);

    // return list sorted by name and uuid
    List<Resume> getAllSorted();

    int size();
}
