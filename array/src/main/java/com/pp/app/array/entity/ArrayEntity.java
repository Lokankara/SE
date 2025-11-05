package com.pp.app.array.entity;

import com.pp.app.array.observer.ArrayObservable;
import com.pp.app.array.observer.Listener;
import com.pp.app.array.validator.ArrayValidator;

import java.util.Arrays;

public abstract class ArrayEntity<T> {

    private final int id;
    private final T[] array;
    private final String name;
    private final ArrayObservable<T> observable = new ArrayObservable<>();

    protected abstract T[] getEmptyArray();

    protected ArrayEntity() {
        this.id = 0;
        this.name = "";
        this.array = getEmptyArray();
        observable.notifyListeners(this);
    }

    protected ArrayEntity(T[] array) {
        this.id = 0;
        this.name = "";
        this.array = ArrayValidator.getIfNull(array.clone(), array);
        observable.notifyListeners(this);
    }

    protected ArrayEntity(int id, String name, T[] array) {
        this.id = id;
        this.name = name;
        this.array = ArrayValidator.getIfNull(array.clone(), getEmptyArray());
        observable.notifyListeners(this);
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

    public T[] getArray() {
        return array.clone();
    }

    public boolean isEmpty() {
        return array.length == 0;
    }

    public T getFirst() {
        return array[0];
    }

    public void setArray(int index, T value) {
        ArrayValidator.validate(index < 0 || index >= getArray().length, "Size out of range");
        array[index] = value;
        observable.notifyListeners(this);
    }

    public void attach(Listener<ArrayEntity<T>> warehouse) {
        observable.attach(warehouse);
    }

    public void removeListener(Listener<ArrayEntity<T>> warehouse) {
        observable.removeListener(warehouse);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;

        ArrayEntity<?> that = (ArrayEntity<?>) o;
        return id == that.id
                && name.equals(that.name)
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
