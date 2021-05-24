package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage<String> {
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
    protected void saveResume(Resume resume, String searchKey) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected boolean isExist(String searchKey) {
        return (searchKey != null);
    }

    @Override
    protected String checkResume(String uuid) {
        return mapStorage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected void deleteResume(String searchKey) {
        mapStorage.remove(searchKey);
    }

    @Override
    protected void replaceResume(Resume resume, String searchKey) {
        mapStorage.replace(searchKey, resume);
    }

    @Override
    protected Resume getResume(String searchKey) {
        return mapStorage.get(searchKey);
    }
}
