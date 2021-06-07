package ru.javawebinar.storage.strategy;

import ru.javawebinar.model.Resume;

import java.io.*;

public interface Strategy {
    void doWrite(Resume resume, OutputStream file) throws IOException;

    Resume doRead(InputStream file) throws IOException;
}
