package com.java.app.array.observer;

public interface Listener<T> {

    void onChanged(T source);
}
