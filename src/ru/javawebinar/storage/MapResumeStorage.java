package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

public class MapResumeStorage extends AbstractMapStorage {
    @Override
    protected Object checkResume(Object searchKey) {
        return mapStorage.containsValue(searchKey) ? searchKey : null;
    }

    @Override
    protected Resume getResume(Object searchKey) {
        if (mapStorage.containsValue(searchKey)) {
            return (Resume) searchKey;
        } else {
            return null;
        }
    }

    @Override
    protected void replaceResume(Resume resume, Object searchKey) {
        Resume r = (Resume) searchKey;
        mapStorage.replace(r.getUuid(), resume);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        Resume r = (Resume) searchKey;
        mapStorage.remove(r.getUuid());
    }
}
