package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortedArrayStorage extends AbstractArrayStorage {
    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());
    private static final Comparator<Resume> RESUME_NAME_COMPARATOR = (o1, o2) -> {
        int nameCompareResult = o1.getFullName().compareTo(o2.getFullName());
        if (nameCompareResult == 0) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
        return nameCompareResult;
    };

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> lStorage = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            lStorage.add(storage[i]);
        }
        lStorage.sort(RESUME_NAME_COMPARATOR);
        return lStorage;
    }

    @Override
    protected Object checkResume(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }

    @Override
    public void saveResumeToArray(Resume resume, int searchKey) {
        searchKey = -(1 + searchKey);
        System.arraycopy(storage, searchKey, storage, searchKey + 1, size - searchKey);
        storage[searchKey] = resume;
    }
}
