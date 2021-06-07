package ru.javawebinar.storage.strategy;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;

import java.io.*;

public class ObjectStreamStrategy implements Strategy {
    @Override
    public void doWrite(Resume resume, OutputStream file) throws IOException {
        try(ObjectOutputStream oos = new ObjectOutputStream(file)) {
            oos.writeObject(resume);
        }
    }

    @Override
    public Resume doRead(InputStream file) throws IOException {
        try(ObjectInputStream ois = new ObjectInputStream(file)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
