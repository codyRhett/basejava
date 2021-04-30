package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> resumeStorage = new ArrayList<>();

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
    protected void replaceResume(Resume resume, Object searchKey) {
        resumeStorage.set((int) searchKey, resume);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return resumeStorage.get((int) searchKey);
    }

    @Override
    protected Object checkResume(Object searchKey) {
        Resume r = (Resume) searchKey;
        for (int i = 0; i < resumeStorage.size(); i++) {
            if (resumeStorage.get(i).getUuid().equals(r.getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected void saveResume(Resume resume, Object searchKey) {
        resumeStorage.add(resume);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        resumeStorage.remove((int) searchKey);
    }
}
