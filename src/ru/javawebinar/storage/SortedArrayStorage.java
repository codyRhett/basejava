package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage{
    public void sortStorage() {
        for (int i = 1; i < size; i++) {
            Resume newElement = storage[i];
            int index = 0;
            index = Arrays.binarySearch(storage, 0, i, newElement);
            if (index < 0) {
                index = -(1 + index);
            }
            System.arraycopy(storage, index, storage, index + 1, i - index);
            storage[index] = newElement;
        }
    }

    @Override
    protected int checkResume(Resume resume) {
        Resume searchKey = new Resume();
        searchKey.setUuid(resume.getUuid());
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
