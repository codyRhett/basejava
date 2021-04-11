package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage {
    HashMap<String, Resume> mapStorage = new HashMap();

    @Override
    protected void checkExist(Resume resume, Object index) {
        if (index != null) {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    protected void checkNotExist(Resume resume, Object index) {
        if (index == null) {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    protected void saveResume(Resume resume, Object index) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected void deleteResume(Object index) {
        mapStorage.remove(index);
    }

    @Override
    protected void setResume(Resume resume, Object index) {
        mapStorage.replace((String)index, resume);
    }

    @Override
    protected Resume getResume(Object index) {
        return mapStorage.get(index);
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
