package com.pp.app.array.service;

import com.pp.app.array.entity.ArrayEntity;
import com.pp.app.array.strategy.SortStrategy;

public interface SortService {

    ArrayEntity sort(ArrayEntity array, SortStrategy strategy);
}
