package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;
import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected Object checkResume(Resume resume) {
        Resume searchKey = new Resume(resume.getUuid());
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    public void saveResumeToArray(Resume resume, int searchKey) {
        searchKey = -(1 + searchKey);
        System.arraycopy(storage, searchKey, storage, searchKey + 1, size - searchKey);
        storage[searchKey] = resume;
    }
}
