package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;
import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int checkResume(Resume resume) {
        Resume searchKey = new Resume();
        searchKey.setUuid(resume.getUuid());
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    public void save(Resume resume) {
        if (isOverflow()) { return; }

        int index = checkResume(resume);
        if (index < 0) {
            index = -(1 + index);
            System.arraycopy(storage, index, storage, index + 1, size - index);
            storage[index] = resume;
            size++;
        } else {
               System.out.println("Резюме " + resume.getUuid() + " уже существует");
        }
    }
}
