package ru.javawebinar.storage.strategy;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

@FunctionalInterface
//public interface WriteInterface<T, U> {
//    void doWriteData(T t, U u) throws IOException;
//}

public interface WriteInterface<T> {
    void doWriteData(T t) throws IOException;
}