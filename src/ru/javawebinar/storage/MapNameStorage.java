package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

public class MapNameStorage extends AbstractMapStorage {
    @Override
    protected Object checkResume(Object searchKey) {
        return mapStorage.containsValue(searchKey) ? searchKey : null;
    }

    @Override
    protected Resume getResume(Object searchKey) {
        for(Resume r : mapStorage.values()) {
            if (r.equals(searchKey)) {
                return r;
            }
        }
        return null;
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
