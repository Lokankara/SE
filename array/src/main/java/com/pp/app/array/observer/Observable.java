package com.pp.app.array.observer;

public interface Observable<T> {

    void attach(Listener<T> listener);

    void removeListener(Listener<T> listener);

    void notifyListeners(T t);
}