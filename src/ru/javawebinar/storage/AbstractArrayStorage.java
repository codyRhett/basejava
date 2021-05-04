package ru.javawebinar.storage;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    protected List<Resume> getResumeList() {
        List<Resume> resumes = new ArrayList<>();
        resumes.addAll(Arrays.asList(storage).subList(0, size));
        return resumes;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected Resume getResume(Integer searchKey) {
        return storage[searchKey];
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    @Override
    protected void replaceResume(Resume resume, Integer searchKey) {
        storage[searchKey] = resume;
    }

    @Override
    protected void deleteResume(Integer searchKey) {
        int index = searchKey;
        System.arraycopy(storage, index + 1, storage, index, size - index);
        size--;
    }

    @Override
    protected void saveResume(Resume resume, Integer searchKey) {
        if (size >= storage.length) {
            throw new StorageException("Массив переполнен ", resume.getUuid());
        }
        saveResumeToArray(resume, searchKey);
        size++;
    }

    protected abstract void saveResumeToArray(Resume resume, int searchKey);
}
