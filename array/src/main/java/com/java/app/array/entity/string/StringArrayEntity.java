package com.java.app.array.entity.string;

import com.java.app.array.observer.ArrayObservable;

import java.util.Arrays;
import java.util.Objects;

public class StringArrayEntity extends ArrayObservable {

    private final int id;
    private final String name;
    private final String[] array;

    public StringArrayEntity(int id, String name, String[] array) {
        this.id = id;
        this.name = name;
        this.array = Arrays.copyOf(array, array.length);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String[] getArray() {
        return array;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;

        StringArrayEntity that = (StringArrayEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Arrays.equals(array, that.array);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }

    @Override
    public String toString() {
        return String.format("StringArrayEntity{id=%d, name='%s', array=%s}",
                id, name, Arrays.toString(array));
    }

    public String getFirst() {
        return array[0];
    }

    public int getLength() {
        return array.length;
    }
}
