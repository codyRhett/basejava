package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListStorage extends AbstractStorage {
    List<Resume> resumeStorage = new ArrayList<>();

    public void save(Resume resume) {
        int index = checkResume(resume);
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            index = -(1 + index);
            resumeStorage.add(index, resume);
        }
    }

    @Override
    public void clearStorage() {
        resumeStorage.clear();
    }

    @Override
    public int getSize() {
        return resumeStorage.size();
    }

    @Override
    public Resume[] getAllResume() {
        Resume [] r = new Resume[resumeStorage.size()];
        resumeStorage.toArray(r);
        return (r);
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
    protected void deleteResume(int index) {
        resumeStorage.remove(index);
    }
}
