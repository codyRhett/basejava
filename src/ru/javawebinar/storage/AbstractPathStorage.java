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
import java.util.function.Consumer;

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

        try {
            Path path = Files.createFile(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }


        File[] files = directory.toFile().listFiles();
        List<Resume> listResume = new ArrayList<>(files.length);

//        for(File file : files) {
//            try {
//                listResume.add(doRead(new BufferedInputStream(new FileInputStream(file))));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return listResume;
    }

    @Override
    protected boolean isExist(Path file) {
        return  Files.exists(file);
    }

    @Override
    protected void saveResume(Resume resume, Path file) {
        try {
            file = Files.createFile(directory);
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void deleteResume(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void replaceResume(Resume resume, Path file) {
        try {
            doWrite(resume, new BufferedReader(new OutputStreamWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Resume getResume(Path file) {
        Resume r = null;
        try {
            r = doRead(new BufferedInputStream(new FileInputStream(String.valueOf(file))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    protected Path checkResume(String uuid) {
        //return new Path(directory, uuid);
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteResume);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int size() {
       // return Objects.requireNonNull(directory.listFiles()).length;
        return 0;
    }

    protected abstract void doWrite(Resume resume, OutputStream file) throws IOException;
    protected abstract Resume doRead(InputStream file) throws IOException;
}
