package org.learn.functional.java.types;

import java.util.function.Supplier;

public class Case<T> extends Tuple<Supplier<Boolean>, Supplier<Result<T>>> {
    private Case(Supplier<Boolean> condition, Supplier<Result<T>> value) {
        super(condition, value);
    }

    public static <T> Case<T> newCase(Supplier<Boolean> conditionSupplier, Supplier<Result<T>> valueSupplier) {
        return new Case<>(conditionSupplier, valueSupplier);
    }

    public static <T> DefaultCase<T> defaultCase(Supplier<Result<T>> valueSupplier) {
        return new DefaultCase<>(valueSupplier);
    }

    public static <T> Result<T> match(DefaultCase<T> defaultCase, Case<T> ...matchers) {
        for (Case<T> aCase : matchers) {
            if (aCase._1.get()) { return aCase._2.get(); }
        }
        return defaultCase._2.get();
    }

    public static class DefaultCase<T> extends Case<T> {
        DefaultCase(Supplier<Result<T>> valueSupplier) {
            super(() -> true, valueSupplier);
        }
    }
}
