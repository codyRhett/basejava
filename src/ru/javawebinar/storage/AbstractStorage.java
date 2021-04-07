package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void save(Resume resume) {
        int index = checkResume(resume);
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveResume(resume, index);
        }
    }

    public void update(Resume resume) {
        int index = checkIndex(resume);
        setResume(resume, index);
    }

    public Resume get(String uuid) {
        int index = checkIndex(new Resume(uuid));
        return getResume(index);
    }

    public void delete(Resume resume) {
        int index = checkIndex(resume);
        deleteResume(index);
    }

    private int checkIndex(Resume resume) {
        int index = checkResume(resume);
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        return index;
    }

    protected abstract void saveResume(Resume resume, int index);
    protected abstract void deleteResume(int index);
    protected abstract void setResume(Resume resume, int index);
    protected abstract Resume getResume(int index);
    protected abstract int checkResume(Resume resume);
}
