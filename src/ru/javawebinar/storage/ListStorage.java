package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> resumeStorage = new ArrayList<>();

    public void clear() {
        resumeStorage.clear();
    }

    public int size() {
        return resumeStorage.size();
    }

    @Override
    protected List<Resume> getResumeList() {
        return resumeStorage;
    }

    @Override
    protected void replaceResume(Resume resume, Integer searchKey) {
        resumeStorage.set(searchKey, resume);
    }

    @Override
    protected Resume getResume(Integer searchKey) {
        return resumeStorage.get(searchKey);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < resumeStorage.size(); i++) {
            if (resumeStorage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return (searchKey >= 0);
    }

    @Override
    protected void saveResume(Resume resume, Integer searchKey) {
        resumeStorage.add(resume);
    }

    @Override
    protected void deleteResume(Integer searchKey) {
        resumeStorage.remove(searchKey.intValue());
    }
}
