package com.java.app.array.entity;

import com.java.app.array.observer.Listener;

import java.util.HashMap;
import java.util.Map;

public class Warehouse implements Listener<ArrayEntity> {

    private static final Warehouse INSTANCE = new Warehouse();
    private final Map<Integer, IntArrayStatistics> statisticsMap;

    private Warehouse() {
        this.statisticsMap = new HashMap<>();
    }

    public static Warehouse getInstance() {
        return INSTANCE;
    }

    public IntArrayStatistics getStatistics(int id) {
        return statisticsMap.get(id);
    }

    public void removeStatistics(int id) {
        statisticsMap.remove(id);
    }

    @Override
    public void onChanged(ArrayEntity arrayEntity) {
        IntArrayStatistics statistics = new IntArrayStatistics(arrayEntity);
        statisticsMap.put(arrayEntity.getId(), statistics);
    }
}
