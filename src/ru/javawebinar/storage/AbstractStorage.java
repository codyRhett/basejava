package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;

import java.util.List;

public abstract class AbstractStorage implements Storage {
    public void save(Resume resume) {
        Object searchKey = checkResume(resume.getUuid());
        if (isExist(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveResume(resume, searchKey);
    }

    public void update(Resume resume) {
        Object searchKey = getSearchKeyIfResumeExist(resume);
        replaceResume(resume, searchKey);
    }

    public Resume get(String uuid) {
        Object searchKey = getSearchKeyIfResumeExist(new Resume(uuid));
        return getResume(searchKey);
    }

    public void delete(Resume resume) {
        Object searchKey = getSearchKeyIfResumeExist(resume);
        deleteResume(searchKey);
    }

    private Object getSearchKeyIfResumeExist(Resume resume) {
        Object searchKey = checkResume(resume.getUuid());
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(resume.getUuid());
        }
        return searchKey;
    }



    protected abstract boolean isExist(Object searchKey);
    protected abstract void saveResume(Resume resume, Object searchKey);
    protected abstract void deleteResume(Object searchKey);
    protected abstract void replaceResume(Resume resume, Object searchKey);
    protected abstract Resume getResume(Object searchKey);
    protected abstract Object checkResume(String uuid);
}
