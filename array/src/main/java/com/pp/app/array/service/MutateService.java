package com.pp.app.array.service;

import com.pp.app.array.entity.ArrayEntity;

public interface MutateService {

    ArrayEntity replace(ArrayEntity array, java.util.function.IntPredicate condition, int newValue);
}
