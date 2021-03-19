package ru.javawebinar.storage;

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
    public void save(Resume resume) {
        if (size >= storage.length) {
            System.out.println("Массив с резюме переполнен");
            return;
        }

        int index = checkResume(resume);
        if (index < 0) {
            storage[size] = resume;
            size++;
        } else {
            System.out.println("Резюме " + resume.getUuid() + " уже существует");
        }
    }
}
