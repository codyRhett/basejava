package ru.javawebinar.storage;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path>{
    private final Path directory;

    protected  AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or not writable");
        }

    }

    @Override
    protected List<Resume> getResumeList() {
        File[] files = directory.toFile().listFiles();
        List<Resume> listResume = new ArrayList<>(files.length);

        for(File file : files) {
            try {
                listResume.add(doRead(new BufferedInputStream(new FileInputStream(file))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return listResume;
        //return null;
    }

    @Override
    protected boolean isExist(Path file) {

        //return file.exists();
        return true;
    }

    @Override
    protected void saveResume(Resume resume, Path file) {
//        try {
//            file.createNewFile();
//            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
//        } catch (IOException e) {
//            throw new StorageException("IO error", file.getName(), e);
//        }
    }

    @Override
    protected void deleteResume(Path file) {
//        if (!file.delete()) {
//            System.out.println("Delete file Error");
//        }
    }

    @Override
    protected void replaceResume(Resume resume, Path file) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Resume getResume(Path file) {
        Resume r = null;
//        try {
//            r = doRead(new BufferedInputStream(new FileInputStream(file)));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return r;
    }

    @Override
    protected Path checkResume(String uuid) {

        //return new File(directory, uuid);
        return null;
    }

    @Override
    public void clear() {
//        File[] files = directory.listFiles();
//        for (File file : files) {
//            if (!file.delete()) {
//                System.out.println("Delete files error");
//            }
//        }
    }

    @Override
    public int size() {
       // return Objects.requireNonNull(directory.listFiles()).length;
        return 0;
    }

    protected abstract void doWrite(Resume resume, BufferedOutputStream file) throws IOException;
    protected abstract Resume doRead(BufferedInputStream file) throws IOException;
}
