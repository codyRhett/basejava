package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> resumeStorage = new ArrayList<>();
    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getFullName().compareTo(o2.getFullName());
    private static final Comparator<Resume> RESUME_NAME_COMPARATOR = (o1, o2) -> {
        int nameCompareResult = o1.getFullName().compareTo(o2.getFullName());
        if (nameCompareResult == 0) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
        return nameCompareResult;
    };

    public void clear() {
        resumeStorage.clear();
    }

    public int size() {
        return resumeStorage.size();
    }

    public Resume[] getAll() {
        Resume [] r = new Resume[resumeStorage.size()];
        resumeStorage.toArray(r);
        return r;
    }

    @Override
    public List<Resume> getAllSorted() {
        resumeStorage.sort(RESUME_NAME_COMPARATOR);
        return resumeStorage;
    }

    @Override
    protected void replaceResume(Resume resume, Object searchKey) {
        resumeStorage.set((int)searchKey, resume);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return resumeStorage.get((int)searchKey);
    }

    @Override
    protected Object checkResume(String uuid) {
        for (int i = 0; i < resumeStorage.size(); i++) {
            if (resumeStorage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int)searchKey >= 0;
    }

    @Override
    protected void saveResume(Resume resume, Object searchKey) {
        resumeStorage.add(resume);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        resumeStorage.remove((int)searchKey);
    }
}
