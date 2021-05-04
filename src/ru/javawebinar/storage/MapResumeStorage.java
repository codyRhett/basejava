package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
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
    protected void saveResume(Resume resume, Resume searchKey) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return (searchKey != null);
    }

    @Override
    protected Resume checkResume(Resume searchKey) {
        return mapStorage.get((searchKey).getUuid());
    }

    @Override
    protected Resume getResume(Resume searchKey) {
        return searchKey;
    }

    @Override
    protected void replaceResume(Resume resume, Resume searchKey) {
        Resume r = searchKey;
        mapStorage.replace(r.getUuid(), resume);
    }

    @Override
    protected void deleteResume(Resume searchKey) {
        Resume r = (Resume) searchKey;
        mapStorage.remove(r.getUuid());
    }
}
