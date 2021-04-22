package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapStorage extends AbstractStorage {
    protected final Map<String, Resume> mapStorage = new HashMap<>();

    public List<Resume> getAllSorted() {
        List<Resume> lStorage = new ArrayList<>();
        lStorage.addAll(mapStorage.values());
        lStorage.sort(RESUME_NAME_COMPARATOR);

        return lStorage;
    }

    public Resume[] getAll() {
        Resume [] r = new Resume[mapStorage.size()];
        mapStorage.values().toArray(r);
        return r;
    }

    public int size() {
        return mapStorage.size();
    }

    public void clear() {
        mapStorage.clear();
    }

    @Override
    protected void replaceResume(Resume resume, Object searchKey) {
        mapStorage.replace((String) searchKey, resume);
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
    protected Object checkResume(String searchKey) {
        return mapStorage.containsKey(searchKey) ? searchKey : null;
    }

    @Override
    protected void deleteResume(Object searchKey) {
        mapStorage.remove(searchKey);
    }
}
