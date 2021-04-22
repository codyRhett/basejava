package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

public class MapStorage extends AbstractMapStorage {
    @Override
    protected void saveResume(Resume resume, Object searchKey) {
        mapStorage.put(resume.getUuid(), resume);
    }
}
