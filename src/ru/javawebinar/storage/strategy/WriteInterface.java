package ru.javawebinar.storage.strategy;

import java.io.IOException;

@FunctionalInterface
public interface WriteInterface<T, U> {
    void doWriteData(T t, U u) throws IOException;
}