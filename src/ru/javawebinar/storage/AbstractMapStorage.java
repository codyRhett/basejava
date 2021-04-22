package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.List;

public class AbstractMapStorage extends AbstractStorage {
    @Override
    protected boolean isExist(Object searchKey) {
        return false;
    }

    @Override
    protected void saveResume(Resume resume, Object searchKey) {

    }

    @Override
    protected void deleteResume(Object searchKey) {

    }

    @Override
    protected void replaceResume(Resume resume, Object searchKey) {

    }

    @Override
    protected Resume getResume(Object searchKey) {
        return null;
    }

    @Override
    protected Object checkResume(String uuid) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public List<Resume> getAllSorted() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
