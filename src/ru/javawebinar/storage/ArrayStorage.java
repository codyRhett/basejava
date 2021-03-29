package ru.javawebinar.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.javawebinar.model.Resume;

import java.util.Arrays;


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
        size++;
    }
}
