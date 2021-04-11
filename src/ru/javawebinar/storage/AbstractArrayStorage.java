package ru.javawebinar.storage;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;
import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    protected Resume getResume(Object searchKey) {
        return storage[(int)searchKey];
    }

    @Override
    protected boolean checkNotExist(Resume resume, Object searchKey) {
        if ((int)searchKey < 0) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean checkExist(Resume resume, Object searchKey) {
        if ((int)searchKey >= 0) {
            return true;
        }
        return false;
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
    protected void replaceResume(Resume resume, Object searchKey) {
        storage[(int)searchKey] = resume;
    }

    @Override
    protected void deleteResume(Object searchKey) {
        int index = (Integer)searchKey;
        System.arraycopy(storage, index + 1, storage, index, size - index);
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
