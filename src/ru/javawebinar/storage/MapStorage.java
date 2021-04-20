package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> mapStorage = new HashMap<>();
    private static final Comparator<Resume> RESUME_NAME_COMPARATOR = (o1, o2) -> {
        int nameCompareResult = o1.getFullName().compareTo(o2.getFullName());
        if (nameCompareResult == 0) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
        return nameCompareResult;
    };

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> lStorage = new ArrayList<>();
        lStorage.addAll(mapStorage.values());

        lStorage.sort(RESUME_NAME_COMPARATOR);
        return lStorage;
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
