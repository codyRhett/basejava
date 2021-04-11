package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;
import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    protected void checkNotExist(Resume resume, Object index) {
        if ((int)index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    protected void checkExist(Resume resume, Object index) {
        if ((int)index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    protected void setResume(Resume resume, Object index) {
        storage[(int)index] = resume;
    }

    @Override
    protected void deleteResume(Object index) {
        System.arraycopy(storage, (int)index + 1, storage, (int)index, size - (int)index);
        size--;
    }

    @Override
    protected void saveResume(Resume resume, Object index) {
        if (size >= storage.length) {
            throw new StorageException("Массив переполнен ", resume.getUuid());
        }
        saveResumeToArray(resume, (int)index);
        size++;
    }

    protected abstract void saveResumeToArray(Resume resume, int index);
}
