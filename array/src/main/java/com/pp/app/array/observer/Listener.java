package com.pp.app.array.observer;

public interface Listener<T> {

    void onChanged(T source);
}
