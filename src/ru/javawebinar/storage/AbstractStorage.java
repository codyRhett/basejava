package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

public abstract class AbstractStorage implements Storage {
    public void save(Resume resume) {
        Object searchKey = checkResume(resume);
        checkExist(resume, searchKey);
        saveResume(resume, searchKey);
    }

    public void update(Resume resume) {
        Object searchKey = checkIndex(resume);
        setResume(resume, searchKey);
    }

    public Resume get(String uuid) {
        Object searchKey = checkIndex(new Resume(uuid));
        return getResume(searchKey);
    }

    public void delete(Resume resume) {
        Object index = checkIndex(resume);
        deleteResume(index);
    }

    private Object checkIndex(Resume resume) {
        Object searchKey = checkResume(resume);
        checkNotExist(resume, searchKey);
        return searchKey;
    }

    protected abstract void checkExist(Resume resume, Object searchKey);
    protected abstract void checkNotExist(Resume resume, Object searchKey);
    protected abstract void saveResume(Resume resume, Object searchKey);
    protected abstract void deleteResume(Object searchKey);
    protected abstract void setResume(Resume resume, Object searchKey);
    protected abstract Resume getResume(Object searchKey);
    protected abstract Object checkResume(Resume resume);
}
