package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;
import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map mapStorage = new HashMap<String, Resume>();

    @Override
    protected boolean checkExist(Resume resume, Object searchKey) {
        if (searchKey != null) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean checkNotExist(Resume resume, Object searchKey) {
        if (searchKey == null) {
            return true;
        }
        return false;
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
    protected void replaceResume(Resume resume, Object index) {
        mapStorage.replace(index, resume);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return (Resume)mapStorage.get(searchKey);
    }

    @Override
    protected Object checkResume(Resume resume) {
        if (mapStorage.containsKey(resume.getUuid())) {
            return resume.getUuid();
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
