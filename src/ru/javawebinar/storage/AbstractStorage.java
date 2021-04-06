package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;

import java.util.Arrays;

public abstract class AbstractStorage implements Storage {


    public void save(Resume resume) {
        checkOverflow(resume);
        int index = checkResume(resume);
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveResume(resume, index);
        }
    }

    public int size() {
        return getSize();
    }

    public void clear() {
        clearStorage();
        //Arrays.fill(storage, 0, size, null);
        //size = 0;
    }

    public void update(Resume resume) {
        int index = checkResume(resume);
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }

        setResumeIndex(resume, index);
        //storage[index] = resume;
    }

    public Resume get(String uuid) {
        Resume resume = new Resume(uuid);
        int index = checkResume(resume);
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        return getResumeIndex(index);
    }

    public void delete(Resume resume) {
        int index = checkResume(resume);
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        //System.arraycopy(storage, index + 1, storage, index, size - index);
        //size--;
    }

    public Resume[] getAll() {
        return null;
        //return Arrays.copyOf(storage, size);
    }

    protected abstract void clearStorage();
    protected abstract int getSize();
    protected abstract void setResumeIndex(Resume resume, int index);
    protected abstract Resume getResumeIndex(int index);
    protected abstract void checkOverflow(Resume resume);
    protected abstract void saveResume(Resume resume, int index);
    protected abstract int checkResume(Resume resume);

}
