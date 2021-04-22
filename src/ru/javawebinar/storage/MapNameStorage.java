package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

public class MapNameStorage extends AbstractMapStorage {
    @Override
    protected void saveResume(Resume resume, Object searchKey) {
        mapStorage.put(resume.getFullName(), resume);
    }
}
