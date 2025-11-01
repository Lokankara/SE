package com.java.app.array.observer;

import com.java.app.array.entity.ArrayEntity;

import java.util.ArrayList;
import java.util.List;

public class ArrayObservable implements Observable<ArrayEntity> {

    private final List<Listener<ArrayEntity>> observers = new ArrayList<>();

    @Override
    public void attach(Listener<ArrayEntity> listener) {
        observers.add(listener);
    }

    @Override
    public void removeListener(Listener<ArrayEntity> listener) {
        observers.remove(listener);
    }

    public void notifyListeners(ArrayEntity entity) {
        observers.forEach(listener -> listener.onChanged(entity));
    }
}
