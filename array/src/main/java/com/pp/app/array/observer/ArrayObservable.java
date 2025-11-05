package com.pp.app.array.observer;

import com.pp.app.array.entity.ArrayEntity;

import java.util.ArrayList;
import java.util.List;

public class ArrayObservable<T> implements Observable<ArrayEntity<T>> {

    private final List<Listener<ArrayEntity<T>>> observers = new ArrayList<>();

    @Override
    public void attach(Listener<ArrayEntity<T>> listener) {
        observers.add(listener);
    }

    @Override
    public void removeListener(Listener<ArrayEntity<T>> listener) {
        observers.remove(listener);
    }

    public void notifyListeners(ArrayEntity<T> entity) {
        observers.forEach(listener -> listener.onChanged(entity));
    }
}
