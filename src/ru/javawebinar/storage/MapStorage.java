package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

public class MapStorage extends AbstractMapStorage {
    @Override
    protected Object checkResume(Object searchKey) {
        Resume r = (Resume) searchKey;
        return mapStorage.containsKey(r.getUuid()) ? r.getUuid() : null;
    }

    @Override
    protected void deleteResume(Object searchKey) {
        mapStorage.remove(searchKey);
    }

    @Override
    protected void replaceResume(Resume resume, Object searchKey) {
        mapStorage.replace((String) searchKey, resume);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return mapStorage.get(searchKey);
    }
}
