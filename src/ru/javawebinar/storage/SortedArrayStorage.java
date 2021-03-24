package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;
import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int checkResume(Resume resume) {
        Resume searchKey = new Resume();
        searchKey.setUuid(resume.getUuid());
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    public void saveResume(Resume resume, int index) {
        index = -(1 + index);
        size++;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }
}
