package org.learn.functional.java.types;

import com.google.common.base.Objects;

public class Tuple<T,U> {
    public final T _1;
    public final U _2;

    public Tuple(T _1, U _2) {
        this._1 = _1;
        this._2 = _2;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s)",_1,_2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equal(_1, tuple._1) &&
                Objects.equal(_2, tuple._2);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(_1, _2);
    }
}
