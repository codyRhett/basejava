package com.webapp.storage;

import com.webapp.model.Resume;

import java.util.Arrays;

public interface Storage {
    void clear();

    void update(Resume resume);

    void save(Resume resume);

    Resume get(Resume resume);

    void delete(Resume resume);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll();

    int size();
}
