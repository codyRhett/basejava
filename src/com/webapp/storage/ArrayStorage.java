package com.webapp.storage;

import com.webapp.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = checkResume(resume);
        if (index == -1) {
            System.out.println("Резюме " + resume.getUuid() + " отсутствует");
            return;
        }
        storage[index] = resume;
    }

    public void save(Resume resume) {
        if (size >= storage.length) {
            System.out.println("Массив с резюме переполнен");
            return;
        }
        int index = checkResume(resume);
        if (index == -1) {
            storage[size] = resume;
            size++;
        } else {
            System.out.println("Резюме " + resume.getUuid() + " уже существует");
        }
    }

    public Resume get(Resume resume) {
        int index = checkResume(resume);
        if (index == -1) {
            System.out.println("Резюме " + resume.getUuid() + " отсутствует");
            return null;
        }
        return storage[index];
    }

    public void delete(Resume resume) {
        int index = checkResume(resume);
        if (index == -1) {
            System.out.println("Резюме " + resume.getUuid() + " отсутствует");
            return;
        }
        System.arraycopy(storage, index + 1, storage, index, size - index);
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    protected int checkResume(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
