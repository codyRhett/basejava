package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
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
    protected void setResume(Resume resume, Object searchKey) {
        resumeStorage.set((int)searchKey, resume);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return resumeStorage.get((int)searchKey);
    }

    @Override
    protected Object checkResume(Resume resume) {
        for (int i = 0; i < resumeStorage.size(); i++) {
            if (resumeStorage.get(i).equals(resume)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void checkExist(Resume resume, Object searchKey) {
        if ((int)searchKey >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    protected void checkNotExist(Resume resume, Object searchKey) {
        if ((int)searchKey < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    protected void saveResume(Resume resume, Object searchKey) {
        resumeStorage.add(resume);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        resumeStorage.remove((int)searchKey);
    }
}
