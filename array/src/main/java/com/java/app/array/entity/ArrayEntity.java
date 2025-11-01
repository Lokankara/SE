package com.java.app.array.entity;

import com.java.app.array.observer.ArrayObservable;
import com.java.app.array.validator.ArrayValidator;

import java.util.Arrays;

public final class ArrayEntity extends ArrayObservable {

    private final int id;
    private final String name;
    private final int[] array;

    public ArrayEntity() {
        this.id = 0;
        this.name = "";
        this.array = updateState(new int[0]);
    }

    private int[] updateState(int[] ints) {
        notifyListeners(this);
        return ArrayValidator.getIfNull(ints.clone(), new int[0]);
    }

    public ArrayEntity(int id, String name, int[] array) {
        this.id = id;
        this.name = name;
        this.array = updateState(array);
    }

    public ArrayEntity(int[] array) {
        this.id = 0;
        this.name = "";
        this.array = updateState(array);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return array.length;
    }

    public int[] getArray() {
        return array.clone();
    }

    public boolean isEmpty() {
        return array.length == 0;
    }

    public int getFirst() {
        return array[0];
    }

    public void setArray(int index, int value) {
        ArrayValidator.validate(index < 0 || index >= array.length, "Size out of range");
        array[index] = value;
        notifyListeners(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;

        ArrayEntity that = (ArrayEntity) o;
        return id == that.id && name.equals(that.name)
                && Arrays.equals(array, that.array);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }

    @Override
    public String toString() {
        return String.format("ArrayEntity{id=%d, name='%s', array=%s}",
                id, name, Arrays.toString(array));
    }
}
