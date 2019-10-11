package org.learn.functional.java.types;

public interface Effect<T> {
    void apply(T t);
}
