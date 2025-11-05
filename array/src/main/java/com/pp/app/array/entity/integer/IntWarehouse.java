package com.pp.app.array.entity.integer;

import com.pp.app.array.entity.ArrayEntity;
import com.pp.app.array.observer.Listener;

import java.util.HashMap;
import java.util.Map;

public class IntWarehouse implements Listener<ArrayEntity<Integer>> {

    private static final IntWarehouse INSTANCE = new IntWarehouse();
    private final Map<Integer, IntArrayStatistics> statisticsMap = new HashMap<>();

    private IntWarehouse() {
    }

    public static IntWarehouse getInstance() {
        return INSTANCE;
    }

    public IntArrayStatistics getStatistics(int id) {
        return statisticsMap.get(id);
    }

    public void removeStatistics(int id) {
        statisticsMap.remove(id);
    }

    @Override
    public void onChanged(ArrayEntity<Integer> source) {
        IntArrayStatistics statistics = new IntArrayStatistics(source);
        statisticsMap.put(source.getId(), statistics);
    }
}
