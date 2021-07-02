package ru.javawebinar.storage.strategy;

import java.io.IOException;

@FunctionalInterface
public interface ReadInterface {
    void read() throws IOException;
}