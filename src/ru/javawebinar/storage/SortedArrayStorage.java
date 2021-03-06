package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "dummy");
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }

    @Override
    public void saveResumeToArray(Resume resume, int searchKey) {
        searchKey = -(1 + searchKey);
        System.arraycopy(storage, searchKey, storage, searchKey + 1, size - searchKey);
        storage[searchKey] = resume;
    }
}
