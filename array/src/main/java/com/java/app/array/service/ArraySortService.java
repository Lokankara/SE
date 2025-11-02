package com.java.app.array.service;

import com.java.app.array.entity.integer.IntArrayEntity;
import com.java.app.array.strategy.SortStrategy;

import java.util.List;

public final class ArraySortService {
    public List<IntArrayEntity> sort(List<IntArrayEntity> arrays, SortStrategy strategy) {
        return strategy.sort(arrays);
    }
}
