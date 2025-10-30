package com.java.app.array.service;

import com.java.app.array.entity.ArrayEntity;

import java.util.function.IntPredicate;

public interface CalculateService {

    long count(ArrayEntity arrayEntity, IntPredicate intPredicate);

    int min(ArrayEntity array);

    int max(ArrayEntity array);

    double average(ArrayEntity array);

    long sum(ArrayEntity array);
}
