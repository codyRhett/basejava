package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;

public abstract class AbstractStorage implements Storage {
    public void save(Resume resume) {
        Object searchKey = checkResume(resume);
        if (checkExist(resume, searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveResume(resume, searchKey);
    }

    public void update(Resume resume) {
        Object searchKey = checkIndex(resume);
        replaceResume(resume, searchKey);
    }

    public Resume get(String uuid) {
        Object searchKey = checkIndex(new Resume(uuid));
        return getResume(searchKey);
    }

    public void delete(Resume resume) {
        Object searchKey = checkIndex(resume);
        deleteResume(searchKey);
    }

    private Object checkIndex(Resume resume) {
        Object searchKey = checkResume(resume);
        if (checkNotExist(resume, searchKey)) {
            throw new NotExistStorageException(resume.getUuid());
        }
        return searchKey;
    }

    protected abstract boolean checkExist(Resume resume, Object searchKey);
    protected abstract boolean checkNotExist(Resume resume, Object searchKey);
    protected abstract void saveResume(Resume resume, Object searchKey);
    protected abstract void deleteResume(Object searchKey);
    protected abstract void replaceResume(Resume resume, Object searchKey);
    protected abstract Resume getResume(Object searchKey);
    protected abstract Object checkResume(Resume resume);
}
