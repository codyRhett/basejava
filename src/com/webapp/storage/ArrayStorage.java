package com.webapp.storage;

import com.webapp.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;
    private int index = 0;

    private boolean checkResume(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                index = i;
                return true;
            }
        }
        return false;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public boolean update(Resume resume) {
        if (!checkResume(resume)) {
            return false;
        }
        storage[index] = resume;
        return true;
    }

    public void save(Resume resume) {
        if (size >= storage.length) {
            System.out.println("Массив с резюме переполнен");
            return;
        }
        if (!checkResume(resume)) {
            storage[size] = resume;
            size++;
        } else {
            System.out.println("Резюме " + resume.getUuid() + " уже существует");
        }
    }

    public Resume get(Resume resume) {
        if (!checkResume(resume)) {
            System.out.println("Резюме " + resume.getUuid() + " отсутствует");
            return null;
        } else {
            return storage[index];
        }
    }

    public boolean delete(Resume resume) {
        if (!checkResume(resume)) {
            return false;
        }
        System.arraycopy(storage, index + 1, storage, index, size - index);
        size--;
        return true;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
