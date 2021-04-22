package ru.javawebinar.storage;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public List<Resume> getAllSorted() {
        List<Resume> lStorage = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            lStorage.add(storage[i]);
        }
        lStorage.sort(RESUME_NAME_COMPARATOR);
        return lStorage;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int)searchKey >= 0;
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storage[(int)searchKey];
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
