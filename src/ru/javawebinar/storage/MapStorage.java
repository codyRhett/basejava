package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;
import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> mapStorage = new HashMap<>();

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
        mapStorage.replace((String) index, resume);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return mapStorage.get(searchKey);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (searchKey != null);
    }

    @Override
    protected Object checkResume(String uuid) {
        return mapStorage.containsKey(uuid) ? uuid : null;
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
