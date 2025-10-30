package com.java.app.array.entity;

import com.java.app.array.observer.Listener;

import java.util.HashMap;
import java.util.Map;

public class Warehouse implements Listener<ArrayEntity> {
    private static Warehouse instance;
    private final Map<Integer, ArrayStatistics> statisticsMap;

    private Warehouse() {
        this.statisticsMap = new HashMap<>();
    }

    public static Warehouse getInstance() {
        if (instance == null) {
            instance = new Warehouse();
        }
        return instance;
    }

    public ArrayStatistics getStatistics(int id) {
        return statisticsMap.get(id);
    }

    public void removeStatistics(int id) {
        statisticsMap.remove(id);
    }

    @Override
    public void onChanged(ArrayEntity arrayEntity) {
        ArrayStatistics statistics = new ArrayStatistics(arrayEntity);
        statisticsMap.put(arrayEntity.getId(), statistics);
    }
}
