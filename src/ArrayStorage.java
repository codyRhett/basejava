import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int cnt = 0;

    void clear() {
        Arrays.fill(storage, 0, cnt, null);
    }

    void save(Resume r) {
        storage[cnt] = r;
        cnt++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < cnt; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < cnt; i++) {
            if (storage[i].uuid.equals(uuid)) {
                cnt--;
                // Нашли элемент, который надо удалить
                for (int j = i; j < (cnt); j++) {
                    storage[j] = storage[j+1];
                }
                storage[cnt] = null;
                return;
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
