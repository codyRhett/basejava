package ru.javawebinar.storage;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected int checkResume(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void setResumeIndex(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected Resume getResumeIndex(int index) {
        return storage[index];
    }

    @Override
    protected void checkOverflow(Resume resume) {
        if (size >= storage.length) {
            throw new StorageException("Массив переполнен ", resume.getUuid());
        }
    }

    @Override
    public void saveResume(Resume resume, int index) {
        storage[size] = resume;
        size++;
    }

    @Override
    public int getSize() {
        return size;
    }

}
