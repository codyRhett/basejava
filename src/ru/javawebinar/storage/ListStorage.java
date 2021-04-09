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

    public Resume[] getAll() {
        Resume [] r = new Resume[resumeStorage.size()];
        resumeStorage.toArray(r);
        return r;
    }

    @Override
    protected void setResume(Resume resume, int index) {
        resumeStorage.set(index, resume);
    }

    @Override
    protected Resume getResume(int index) {
        return resumeStorage.get(index);
    }

    @Override
    protected int checkResume(Resume resume) {
        for (int i = 0; i < resumeStorage.size(); i++) {
            if (resumeStorage.get(i).equals(resume)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void saveResume(Resume resume, int index) {
        resumeStorage.add(resume);
    }

    @Override
    protected void deleteResume(int index) {
        resumeStorage.remove(index);
    }
}
