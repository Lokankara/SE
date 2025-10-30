package com.java.app.array.service;

import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.strategy.SortStrategy;

public interface SortService {

    ArrayEntity sort(ArrayEntity array, SortStrategy strategy);
}
