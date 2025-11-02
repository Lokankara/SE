package com.java.app.array.service;

import com.java.app.array.entity.integer.IntArrayEntity;

import java.util.function.IntPredicate;

public interface CalculateService {

    long count(IntArrayEntity arrayEntity, IntPredicate intPredicate);

    int min(IntArrayEntity array);

    int max(IntArrayEntity array);

    double average(IntArrayEntity array);

    long sum(IntArrayEntity array);
}
