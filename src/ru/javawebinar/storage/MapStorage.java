package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage {
    HashMap<String, Resume> mapStorage = new HashMap();

    @Override
    protected void checkExist(Resume resume, Object searchKey) {
        if (searchKey != null) {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    protected void checkNotExist(Resume resume, Object searchKey) {
        if (searchKey == null) {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    protected void saveResume(Resume resume, Object searchKey) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        mapStorage.remove(searchKey);
    }

    @Override
    protected void setResume(Resume resume, Object index) {
        mapStorage.replace((String)index, resume);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return mapStorage.get(searchKey);
    }

    @Override
    protected Object checkResume(Resume resume) {
        for (Resume r : mapStorage.values()) {
            if (mapStorage.get(r.getUuid()).equals(resume)) {
                return r.getUuid();
            }
        }
        return null;
    }

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume [] r = new Resume[mapStorage.size()];
        mapStorage.values().toArray(r);
        return r;
    }

    @Override
    public int size() {
        return mapStorage.size();
    }
}
