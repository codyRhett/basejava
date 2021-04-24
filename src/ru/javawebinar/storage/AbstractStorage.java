package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;
import java.util.Comparator;

public abstract class AbstractStorage implements Storage {
    protected static final Comparator<Resume> RESUME_NAME_COMPARATOR = (o1, o2) -> {
        int nameCompareResult = o1.getFullName().compareTo(o2.getFullName());
        if (nameCompareResult == 0) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
        return nameCompareResult;
    };

    public void save(Resume resume) {
        Object searchKey = checkResume(resume);
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
        Object searchKey = checkResume(resume);
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
    protected abstract Object checkResume(Object searchKey);
}
