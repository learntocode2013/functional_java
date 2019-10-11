package org.learn.functional.java.types;

import java.io.Serializable;
import java.util.function.Function;

public abstract class Result<T> implements Serializable {
    public abstract boolean isEmpty();
    public abstract boolean isSuccess();
    public abstract boolean isFailure();
    public abstract T getOrElse(T defaultVal);
    public abstract T successValue();
    public abstract RuntimeException failureValue();
    public abstract void forEach(Effect<T> e);
    public abstract void bind(Effect<T> success, Effect<String> failure);
    public abstract Result<String> forEachOrFail(Effect<T> e);
    public abstract <U> Result<U> map(Function<T, U> f);
    public abstract <U> Result<U> flatMap(Function<T, Result<U>> f);
    public abstract Result<T> mapFailure(String s);
    public static <T> Empty<T> empty() { return new Empty<>(); }
    public static <T> Result<T> success(T val) {
        return new Success<>(val);
    }
    public static <T,U> Result<T> failure(Failure<U> failure) { return failure(failure.exception); }
    public static <T> Failure<T> failure(String message) {
        return new Failure<>(message);
    }
    public static <T> Failure<T> failure(String errMessage, Exception cause) {
        return new Failure<>(new IllegalStateException(errMessage, cause));
    }
    public static <T> Failure<T> failure(Exception cause) {
        return new Failure<T>(cause);
    }
    public static <T> Result<T> of(Function<T, Boolean> predicate, T value, String message) {
        try {
            return predicate.apply(value) ?
                    new Success<>(value) :
                    new Failure<>(String.format(message, value));
        } catch (Exception cause) {
            String errMessage = "Failed to execute predicate for " + value + " due to: " + cause.getMessage();
            return Result.failure(errMessage, cause);
        }
    }

    public static <T> Result<T> of(T val) {
        return val != null ? success(val) : failure("No value");
    }

    private static class Empty<T> extends Result<T> {
        Empty() {
            super();
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public boolean isFailure() {
            return false;
        }

        @Override
        public T getOrElse(T defaultVal) {
            return defaultVal;
        }

        @Override
        public T successValue() {
            throw new IllegalStateException("Method successValue was called on an empty instance !");
        }

        @Override
        public RuntimeException failureValue() {
            throw new IllegalStateException("Method failureValue was called on an empty instance !");
        }

        @Override
        public void forEach(Effect<T> e) {
            // do nothing
        }

        @Override
        public void bind(Effect<T> success, Effect<String> failure) {
            // do nothing
        }

        @Override
        public Result<String> forEachOrFail(Effect<T> e) {
            return empty();
        }

        @Override
        public <U> Result<U> map(Function<T, U> f) {
            return empty();
        }

        @Override
        public <U> Result<U> flatMap(Function<T, Result<U>> f) {
            return empty();
        }

        @Override
        public Result<T> mapFailure(String s) {
            return failure(s);
        }

        @Override
        public String toString() { return "Empty"; }
    }

    private static class Success<T> extends Result<T> {
        private final T val;

        Success(T val) {
            super();
            this.val = val;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public boolean isFailure() {
            return false;
        }

        @Override
        public T getOrElse(T defaultVal) {
            return successValue();
        }

        @Override
        public T successValue() {
            return val;
        }

        @Override
        public RuntimeException failureValue() {
            throw new IllegalStateException("Method failureValue was called on a success instance");
        }

        @Override
        public void forEach(Effect<T> e) {
            e.apply(successValue());
        }

        @Override
        public void bind(Effect<T> success, Effect<String> failure) {
            success.apply(successValue());
        }

        @Override
        public Result<String> forEachOrFail(Effect<T> e) {
            e.apply(successValue());
            return empty();
        }

        @Override
        public <U> Result<U> map(Function<T, U> f) {
            try {
                return success(f.apply(successValue()));
            } catch (Exception cause) {
                return failure(cause.getMessage(), cause);
            }
        }

        @Override
        public <U> Result<U> flatMap(Function<T, Result<U>> f) {
            try {
                return f.apply(successValue());
            } catch (Exception cause) {
                return failure(cause.getMessage(), cause);
            }
        }

        @Override
        public Result<T> mapFailure(String s) {
            return this;
        }

        @Override
        public String toString() { return "Success"; }
    }

    private static class Failure<T> extends Empty<T> {
        private final RuntimeException exception;

        Failure(RuntimeException exception) {
            super();
            this.exception = exception;
        }

        Failure(String message) {
            super();
            this.exception = new IllegalStateException(message);
        }

        private Failure(Exception cause) {
            super();
            this.exception = new IllegalStateException(cause);
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public boolean isFailure() {
            return true;
        }

        @Override
        public T successValue() {
            throw new IllegalStateException("Method successValue was called on a failure instance !");
        }

        @Override
        public RuntimeException failureValue() {
            return this.exception;
        }

        @Override
        public <U> Result<U> flatMap(Function<T, Result<U>> f) {
            return failure(exception.getMessage(), exception);
        }

        public <U> Result<U> map(Function<T,U> f) {
            return failure(this);
        }

        public Result<String> forEachOrFail(Effect<T> e) {
            return success(exception.getMessage());
        }

        public Result<T> mapFailure(String s) {
            return failure(s, exception);
        }

        @Override
        public void bind(Effect<T> success, Effect<String> failure) {
            failure.apply(exception.getMessage());
        }

        @Override
        public String toString() { return "Failure"; }
    }
}
