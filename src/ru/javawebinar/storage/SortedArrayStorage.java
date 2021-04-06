package ru.javawebinar.storage;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;
import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int checkResume(Resume resume) {
        Resume searchKey = new Resume(resume.getUuid());
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void setResumeIndex(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected Resume getResumeIndex(int index) {
        index = -(1 + index);
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
        index = -(1 + index);
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
        size++;
    }



    @Override
    public int getSize() {
        return size;
    }
}
