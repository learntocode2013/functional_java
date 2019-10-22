package org.learn.functional.java.common;

import org.learn.functional.java.types.Result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.Arrays.copyOf;
import static org.learn.functional.java.types.Case.*;

public class CollectionUtilities {
    public static <T> List<T> list() {
        return Collections.unmodifiableList(Collections.EMPTY_LIST);
    }

    public static <T> List<T> list(T elem) {
        return Collections.singletonList(elem);
    }

    public static <T> List<T> list(T ...elems) {
        return Collections.unmodifiableList(asList(copyOf(elems, elems.length)));
    }

    private static <T> List<T> copy(List<T> source) {
        return new ArrayList<>(source);
    }

    public static <T> Result<T> head(List<T> source) {
        return match(defaultCase(() -> Result.success(source.get(0))),
                newCase(() -> source == null, () -> Result.failure("Source list is not valid")),
                newCase(() -> source.isEmpty(), () -> Result.failure("Source list cannot be empty"))
                );
    }

    public static <T> Result<List<T>> tail(List<T> source) {
        return match(defaultCase(() -> Result
                .success(Collections.unmodifiableList(allButFirst(source)))),
                newCase(() -> source == null, () -> Result.failure("Source list is not valid")),
                newCase(() -> source.isEmpty(), () -> Result.failure("Source list cannot be empty")),
                newCase(() -> source.size() == 1, () -> Result.failure("Source list cannot be empty"))
                );
    }

    private static <T> List<T> allButFirst(List<T> source) {
        List<T> workList = copy(source);
        workList.remove(0);
        return workList;
    }

    public static <T,U> U foldLeft(Collection<T> source, U identity,
                                   Function<U, Function<T, U>> fn) {
        U result = identity;
        for (T elem : source) {
            result = fn.apply(result).apply(elem);
        }
        return result;
    }

    public static <T,U> U foldRight(List<T> source, U identity, Function<T, Function<U, U>> fn) {
        U result = identity;
        for (int i = source.size() - 1; i >= 0; i--) {
            result = fn.apply(source.get(i)).apply(result);
        }
        return result;
    }

    public static <T> List<T> append(List<T> source, T elem) {
        List<T> workList = copy(source);
        workList.add(elem);
        return Collections.unmodifiableList(workList);
    }

    public static <T> List<T> prepend(T elem, List<T> target) {
        List<T> result = foldLeft(target, list(elem), a -> b -> append(a, b));
        return Collections.unmodifiableList(result);
    }

    public static <T> List<T> reverse(List<T> source) {
        List<T> result = foldRight(source, list(), a -> b -> append(b, a));
        return Collections.unmodifiableList(result);
    }
}
