package com.pp.app.array.entity.string;

import com.pp.app.array.entity.ArrayEntity;
import com.pp.app.array.observer.Listener;

import java.util.HashMap;
import java.util.Map;

public class StringWarehouse implements Listener<ArrayEntity<String>> {

    private static final StringWarehouse INSTANCE = new StringWarehouse();
    private final Map<Integer, StringArrayStatistics> statisticsMap = new HashMap<>();

    private StringWarehouse() {
    }

    public static StringWarehouse getInstance() {
        return INSTANCE;
    }

    public StringArrayStatistics getStatistics(int id) {
        return statisticsMap.get(id);
    }

    public void removeStatistics(int id) {
        statisticsMap.remove(id);
    }

    @Override
    public void onChanged(ArrayEntity<String> source) {
        StringArrayStatistics statistics = new StringArrayStatistics(source);
        statisticsMap.put(source.getId(), statistics);
    }
}
