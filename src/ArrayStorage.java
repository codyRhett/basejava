import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int cnt = 0;

    void clear() {
        for (int i = 0; i < (cnt-1); i++) {
            storage[i] = null;
        }
    }

    void save(Resume r) {
        storage[cnt] = r;
        cnt++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < cnt; i++) {
            if (storage[i].uuid == uuid) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < (cnt - 1); i++) {
            if (storage[i].uuid == uuid) {
                storage[i] = null;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, cnt);
    }

    int size() {
        return storage.length;
    }
}
