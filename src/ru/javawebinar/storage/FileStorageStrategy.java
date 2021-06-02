package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public interface FileStorageStrategy {
    void doWrite(Resume resume, BufferedOutputStream file) throws IOException;

    Resume doRead(BufferedInputStream file) throws IOException;
}
