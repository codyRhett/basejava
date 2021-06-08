package ru.javawebinar.storage;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;
import ru.javawebinar.storage.strategy.Strategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
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
    public void clear() {
        getFilesStream().forEach(this::deleteResume);
    }

    @Override
    public int size() {
        return (int) getFilesStream().count();
    }

    @Override
    protected List<Resume> getResumeList() {
        return getFilesStream()
            .map(this::getResume)
            .collect(Collectors.toList());
    }

    @Override
    protected boolean isExist(Path path) {
        return  Files.exists(path);
    }

    @Override
    protected void saveResume(Resume resume, Path path) {
        try {
            Files.createFile(path);
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
        try {
            return fss.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("File read error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    private Stream<Path> getFilesStream() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", directory.getFileName().toString(), null);
        }
    }
}
