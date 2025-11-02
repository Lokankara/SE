package com.java.app.array.entity.request;

import java.util.Arrays;
import java.util.Objects;

public final class CreateRequest<T> {

    private T[] array;
    private String name;

    public String getName() {
        return name;
    }

    public T[] getArray() {
        return array;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;

        CreateRequest<?> that = (CreateRequest<?>) o;
        return Objects.equals(name, that.name) && Arrays.equals(array, that.array);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }
}