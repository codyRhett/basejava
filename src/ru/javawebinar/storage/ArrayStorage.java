package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;
/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected Object checkResume(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Resume getResume(Object index) {
        return storage[(int)index];
    }


    @Override
    protected void saveResumeToArray(Resume resume, int index) {
        storage[size] = resume;
    }
}
