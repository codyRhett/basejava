package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;
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
    public void saveResume(Resume resume, int index) {
        storage[size] = resume;
    }
}
