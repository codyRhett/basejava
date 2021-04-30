package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapStorage extends AbstractStorage {
    protected final Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    protected List<Resume> getResumeList() {
        return new ArrayList<>(mapStorage.values());
    }

    public int size() {
        return mapStorage.size();
    }

    public void clear() {
        mapStorage.clear();
    }

    @Override
    protected void saveResume(Resume resume, Object searchKey) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (searchKey != null);
    }
}
