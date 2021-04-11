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
    protected void checkNotExist(Resume resume, Object searchKey) {
        if ((int)searchKey < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    protected void checkExist(Resume resume, Object searchKey) {
        if ((int)searchKey >= 0) {
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
    protected void setResume(Resume resume, Object searchKey) {
        storage[(int)searchKey] = resume;
    }

    @Override
    protected void deleteResume(Object searchKey) {
        System.arraycopy(storage, (int)searchKey + 1, storage, (int)searchKey, size - (int)searchKey);
        size--;
    }

    @Override
    protected void saveResume(Resume resume, Object searchKey) {
        if (size >= storage.length) {
            throw new StorageException("Массив переполнен ", resume.getUuid());
        }
        saveResumeToArray(resume, (int)searchKey);
        size++;
    }

    protected abstract void saveResumeToArray(Resume resume, int searchKey);
}
