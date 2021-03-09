package ArrayStorage;

import Resume.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public boolean update(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    public void save(Resume r) {
        if (size >= storage.length) {
            System.out.println("Массив с резюме переполнен");
            return;
        }
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(r.toString())) {
                System.out.println("Резюме " + r.toString() + " отсутствует");
                return;
            }
        }
        storage[size] = r;
        size++;

    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                return storage[i];
            }
        }
        System.out.println("Резюме " + uuid + " отсутствует");
        return null;
    }

    public boolean delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                System.arraycopy(storage, i + 1, storage, i, size - i);
                size--;
                return true;
            }
        }
        return false;
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
