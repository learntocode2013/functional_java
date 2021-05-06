package org.learn.functional.java.concepts;

@FunctionalInterface
public interface TriFunction<T1, T2,T3,R> {
    R apply(T1 input1, T2 input2, T3 input3);
}
