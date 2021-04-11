package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

public abstract class AbstractStorage implements Storage {
    public void save(Resume resume) {
        Object index = checkResume(resume);
        checkExist(resume, index);
        saveResume(resume, index);
//        if (index >= 0) {
//            throw new ExistStorageException(resume.getUuid());
//        } else {
//            saveResume(resume, index);
//        }
    }

    public void update(Resume resume) {
        Object index = checkIndex(resume);
        setResume(resume, index);
    }

    public Resume get(String uuid) {
        Object index = checkIndex(new Resume(uuid));
        return getResume(index);
    }

    public void delete(Resume resume) {
        Object index = checkIndex(resume);
        deleteResume(index);
    }

    private Object checkIndex(Resume resume) {
        Object index = checkResume(resume);
        checkNotExist(resume, index);
//        if (index < 0) {
//            throw new NotExistStorageException(resume.getUuid());
//        }
        return index;
    }

    protected abstract void checkExist(Resume resume, Object index);
    protected abstract void checkNotExist(Resume resume, Object index);

    protected abstract void saveResume(Resume resume, Object index);
    protected abstract void deleteResume(Object index);
    protected abstract void setResume(Resume resume, Object index);
    protected abstract Resume getResume(Object index);
    protected abstract Object checkResume(Resume resume);
}
