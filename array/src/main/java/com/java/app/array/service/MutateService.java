package com.java.app.array.service;

import com.java.app.array.entity.ArrayEntity;

public interface MutateService {

    ArrayEntity replace(ArrayEntity array, java.util.function.IntPredicate condition, int newValue);
}
