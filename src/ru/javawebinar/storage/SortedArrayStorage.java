package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage{
    public void sortStorage() {
        for (int i = 1; i < size; i++) {
            Resume newElement = storage[i];
            int index = 0;
            index = Arrays.binarySearch(storage, 0, i, newElement);
            if (index < 0) {
                index = -(1 + index);
            }
            System.arraycopy(storage, index, storage, index + 1, i - index);
            storage[index] = newElement;
        }
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void update(Resume resume) {
        int index = checkResume(resume);
        if (index < 0) {
            System.out.println("Резюме " + resume.getUuid() + " отсутствует");
            return;
        }
        storage[index] = resume;
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

    @Override
    public Resume get(Resume resume) {
        int index = checkResume(resume);
        if (index < 0) {
            System.out.println("Резюме " + resume.getUuid() + " отсутствует");
            return null;
        }
        return storage[index];
    }

    @Override
    public void delete(Resume resume) {
        int index = checkResume(resume);
        if (index < 0) {
            System.out.println("Резюме " + resume.getUuid() + " отсутствует");
            return;
        }
        System.arraycopy(storage, index + 1, storage, index, size - index);
        size--;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    protected int checkResume(Resume resume) {
        Resume searchKey = new Resume();
        searchKey.setUuid(resume.getUuid());
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
