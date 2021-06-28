package ru.javawebinar.util;

import java.io.IOException;

@FunctionalInterface
public interface WriteInterface<T, U> {
    void doWriteInt(T t, U u) throws IOException;
}
