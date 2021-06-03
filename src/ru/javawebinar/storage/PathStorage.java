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
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path>{
    private final Path directory;
    Strategy fss;

    protected PathStorage(String dir, Strategy fss) {
        this.fss = fss;
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or not writable");
        }
    }

    @Override
    protected List<Resume> getResumeList() {
        List<Resume> listResume = null;
        try {
            List<Path> listPath = Files.list(directory).collect(Collectors.toList());
            listResume = new ArrayList<>((int) Files.list(directory).count());

            for (Path path : listPath) {
                listResume.add(fss.doRead(new BufferedInputStream(Files.newInputStream(path))));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listResume;
    }

    @Override
    protected boolean isExist(Path path) {
        return  Files.exists(path);
    }

    @Override
    protected void saveResume(Resume resume, Path path) {
        try {
            path = Files.createFile(directory);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + path, path.getFileName().toString(), e);
        }
        replaceResume(resume, path);
    }

    @Override
    protected void deleteResume(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("File delete error", path.getFileName().toString());
        }
    }

    @Override
    protected void replaceResume(Resume resume, Path path) {
        try {
            fss.doWrite(resume,  new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    protected Resume getResume(Path path){
        Resume r;
        try {
            r = fss.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("File read error", path.getFileName().toString(), e);
        }
        return r;
    }

    @Override
    protected Path getSearchKey(String uuid) {
       return directory;
    }

    @Override
    public void clear() {
        try {
            List<Path> paths = Files.list(directory).collect(Collectors.toList());
            for (Path file : paths) {
                deleteResume(file);
            }
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }

    @Override
    public int size() {
        int size = 0;
        try {
            size = (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
        return size;
    }
}
