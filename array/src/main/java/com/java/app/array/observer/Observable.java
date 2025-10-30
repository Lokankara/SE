package com.java.app.array.observer;

public interface Observable<T> {

    void addListener(Listener<T> listener);

    void removeListener(Listener<T> listener);

    void notifyListeners();
}