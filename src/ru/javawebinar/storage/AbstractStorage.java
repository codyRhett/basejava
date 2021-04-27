package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;
import java.util.Comparator;
import java.util.List;

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
        Object searchKey = getSearchKeyIfResumeExist(resume.getUuid());
        replaceResume(resume, searchKey);
    }

    public Resume get(String uuid) {
        Object searchKey = getSearchKeyIfResumeExist(uuid);
        return getResume(searchKey);
    }

    public void delete(Resume resume) {
        Object searchKey = getSearchKeyIfResumeExist(resume.getUuid());
        deleteResume(searchKey);
    }

    public List<Resume> getAllSorted() {
        List<Resume> lStorage = getResumeList();
        lStorage.sort(RESUME_NAME_COMPARATOR);
        return lStorage;
    }

    private Object getSearchKeyIfResumeExist(String uuid) {
        Object searchKey = checkResume(new Resume(uuid));
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract List<Resume> getResumeList();
    protected abstract boolean isExist(Object searchKey);
    protected abstract void saveResume(Resume resume, Object searchKey);
    protected abstract void deleteResume(Object searchKey);
    protected abstract void replaceResume(Resume resume, Object searchKey);
    protected abstract Resume getResume(Object searchKey);
    protected abstract Object checkResume(Object searchKey);
}
