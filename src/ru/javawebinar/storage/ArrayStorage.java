package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
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
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void saveResumeToArray(Resume resume, int searchKey) {
        storage[size] = resume;
    }
}
