package ru.javawebinar.storage;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;

import java.io.*;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File>{
    private final File directory;

    protected  AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected List<Resume> getResumeList() {
        String[] strFiles = directory.list();
        List<Resume> listResume = null;

        if (strFiles != null) {
            for(String str : strFiles) {
                try {
                    listResume.add(doRead(new File(str)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return listResume;
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void saveResume(Resume resume, File file) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void deleteResume(File file) {
       if (!file.delete()) {
           System.out.println("Delete file Error");
       }
    }

    @Override
    protected void replaceResume(Resume resume, File file) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Resume getResume(File file) {
        Resume r = null;
        try {
            r = doRead(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    protected File checkResume(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    public void clear() {
        if (!directory.delete()) {
            System.out.println("Delete files error");
        }
    }

    @Override
    public int size() {
        return Objects.requireNonNull(directory.listFiles()).length;
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;
    protected abstract Resume doRead(File file) throws IOException;
}
