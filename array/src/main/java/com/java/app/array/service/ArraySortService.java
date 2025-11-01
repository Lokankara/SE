package com.java.app.array.service;

import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.strategy.SortStrategy;

import java.util.List;

public final class ArraySortService {
    public List<ArrayEntity> sort(List<ArrayEntity> arrays, SortStrategy strategy) {
        return strategy.sort(arrays);
    }
}
