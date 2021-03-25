package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;
import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = checkResume(resume);
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
            //System.out.println("Резюме " + resume.getUuid() + " отсутствует");
            //return;
        }
        storage[index] = resume;
    }

    public Resume get(Resume resume) {
        int index = checkResume(resume);
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
            //System.out.println("Резюме " + resume.getUuid() + " отсутствует");
            //return null;
        }
        return storage[index];
    }

    public void delete(Resume resume) {
        int index = checkResume(resume);
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
            //System.out.println("Резюме " + resume.getUuid() + " отсутствует");
            //return;
        }
        System.arraycopy(storage, index + 1, storage, index, size - index);
        size--;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public void save(Resume resume) {
        if (size >= storage.length) {
            throw new StorageException("Массив переполнен ", resume.getUuid());
            //System.out.println("Массив с резюме переполнен");
        }
        int index = checkResume(resume);
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
            //System.out.println("Резюме " + resume.getUuid() + " уже существует");
        } else {
            saveResume(resume, index);
        }
    }

    protected abstract void saveResume(Resume resume, int index);
    protected abstract int checkResume(Resume resume);
}
