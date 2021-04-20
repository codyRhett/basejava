package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.List;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    public List<Resume> getAllSorted() {

        return null;
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
