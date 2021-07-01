package ru.javawebinar.storage.strategy;

import java.io.IOException;

@FunctionalInterface
public interface ReadInterface<T> {
    void read(T t) throws IOException;
}