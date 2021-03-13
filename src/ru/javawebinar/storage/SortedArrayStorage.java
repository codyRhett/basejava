package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage{
    public void sortResume() {
        for (int i = 0; i < size(); i++) {
            Resume resume = storage[i];
            int index = Arrays.binarySearch(storage, 0, size, resume);
            System.arraycopy(storage, index, storage, index+1, i - index);
            storage[index] = resume;
        }

    }

    @Override
    public void clear() {

    }

    @Override
    public void update(Resume resume) {

    }

    @Override
    public void save(Resume resume) {

    }

    @Override
    public Resume get(Resume resume) {
        return null;
    }

    @Override
    public void delete(Resume resume) {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    protected int checkResume(Resume resume) {
        Resume searchKey = new Resume();
        searchKey.setUuid(resume.getUuid());
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
