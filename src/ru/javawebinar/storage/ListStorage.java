package ru.javawebinar.storage;

import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage{
    protected ArrayList<Resume> listStorage = new ArrayList();

    public void save(Resume resume) {
        listStorage.add(resume);
    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public void update(Resume resume) {

    }

    public Resume get(String uuid) {
        Resume resume = new Resume(uuid);
        int index = checkResume(resume);
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        return (Resume)listStorage.get(index);
    }

    @Override
    public void delete(Resume resume) {

    }

    @Override
    protected int checkResume(Resume resume) {
        Iterator<Resume> iterator = listStorage.iterator();
        int index = 0;
        while(iterator.hasNext()) {
            Resume r = iterator.next();
            if (Objects.equals(r, resume)) {
                return index;
            }
            index++;
        }
        return -1;
    }
}
