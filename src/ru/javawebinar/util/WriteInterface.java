package ru.javawebinar.util;

@FunctionalInterface
public interface WriteInterface<T> {
    T doWriteInt(T t);
}
