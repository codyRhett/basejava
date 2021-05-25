package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected static final Comparator<Resume> RESUME_NAME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public void save(Resume resume) {
        LOG.info("save" + resume);
        SK searchKey = checkResume(resume.getUuid());
        if (isExist(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveResume(resume, searchKey);
    }

    public void update(Resume resume) {
        LOG.info("update" + resume);
        SK searchKey = getSearchKeyIfResumeExist(resume.getUuid());
        replaceResume(resume, searchKey);
    }

    public Resume get(String uuid) {
        LOG.info("update" + uuid);
        SK searchKey = getSearchKeyIfResumeExist(uuid);
        return getResume(searchKey);
    }

    public void delete(String uuid) {
        LOG.info("update" + uuid);
        SK searchKey = getSearchKeyIfResumeExist(uuid);
        deleteResume(searchKey);
    }

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> resumes = null;
        try {
            resumes = getResumeList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        resumes.sort(RESUME_NAME_COMPARATOR);
        return resumes;
    }

    private SK getSearchKeyIfResumeExist(String uuid) {
        LOG.info("getSearchKeyIfResumeExist");
        SK searchKey = checkResume(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Резюме " + uuid + " не существует");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract List<Resume> getResumeList() throws IOException;

    protected abstract boolean isExist(SK searchKey);

    protected abstract void saveResume(Resume resume, SK searchKey);

    protected abstract void deleteResume(SK searchKey);

    protected abstract void replaceResume(Resume resume, SK searchKey);

    protected abstract Resume getResume(SK searchKey);

    protected abstract SK checkResume(String uuid);
}
