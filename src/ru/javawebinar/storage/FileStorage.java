package ru.javawebinar.storage;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;
import ru.javawebinar.storage.strategy.Strategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    Strategy fss;

    protected FileStorage(File directory, Strategy fss) {
        this.fss = fss;
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
    public void clear() {
        for (File file : getFilesArray()) {
            deleteResume(file);
        }
    }

    @Override
    public int size() {
        return getFilesArray().length;
    }

    @Override
    protected List<Resume> getResumeList() {
        File[] files = getFilesArray();
        List<Resume> listResume = new ArrayList<>(files.length);

        for(File file : files) {
            try {
                listResume.add(fss.doRead(new BufferedInputStream(new FileInputStream(file))));
            } catch (IOException e) {
                throw new StorageException("Directory read error", null);
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
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        replaceResume(resume, file);
    }

    @Override
    protected void deleteResume(File file) {
       if (!file.delete()) {
           throw new StorageException("File delete error", file.getName());
       }
    }

    @Override
    protected void replaceResume(Resume resume, File file) {
        try {
            fss.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return fss.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    private File[] getFilesArray() {
        File[] files = directory.listFiles();
        if  (files == null) {
            throw new StorageException("Directory read error", directory.getAbsolutePath());
        }
        return files;
    }
}
