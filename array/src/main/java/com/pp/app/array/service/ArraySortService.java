package com.pp.app.array.service;

import com.pp.app.array.entity.integer.IntArrayEntity;
import com.pp.app.array.strategy.SortStrategy;

import java.util.List;

public final class ArraySortService {
    public List<IntArrayEntity> sort(List<IntArrayEntity> arrays, SortStrategy strategy) {
        return strategy.sort(arrays);
    }
}
