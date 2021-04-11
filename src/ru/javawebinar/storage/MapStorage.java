package ru.javawebinar.storage;

import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage {
    HashMap<String, Resume> mapStorage = new HashMap();
    //Resume[] all = mapStorage.;

    @Override
    protected void checkExist(Resume resume, Object index) {

    }

    @Override
    protected void checkNotExist(Resume resume, Object index) {
        if (index == null) {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    protected void saveResume(Resume resume, Object index) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected void deleteResume(Object index) {

    }

    @Override
    protected void setResume(Resume resume, Object index) {

    }

    @Override
    protected Resume getResume(Object index) {
        return mapStorage.get(index);
    }

    @Override
    protected Object checkResume(Resume resume) {
        for (Resume r : mapStorage.values()) {
            if (mapStorage.get(r.getUuid()).equals(resume)) {
                return r.getUuid();
            }
        }
//        for (int i = 0; i < mapStorage.size(); i++) {
//            if (mapStorage.get(i).equals(resume)) {
//                return i;
//            }
//        }
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
    public int size() {
        return 0;
    }
}
