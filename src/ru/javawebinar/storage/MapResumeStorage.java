package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

public class MapResumeStorage extends AbstractMapStorage {
    @Override
    protected Object checkResume(Object searchKey) {
        return mapStorage.get(((Resume) searchKey).getUuid());
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return (Resume) searchKey;
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
